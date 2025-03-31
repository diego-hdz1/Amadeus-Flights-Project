package com.Encora.AmadeusBackend.Service;

import com.Encora.AmadeusBackend.Model.AirportCode;
import com.Encora.AmadeusBackend.Model.Flight;
import com.Encora.AmadeusBackend.Model.FlightDetails;
import com.Encora.AmadeusBackend.Model.Segments;
import com.Encora.AmadeusBackend.Repo.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

@Service
public class FlightServiceImpl implements FlightService{

    //TO DO: Change this autowired as Mirna said
    @Autowired
    FlightRepository flightRepository;

    final String CLIENT_ID = System.getenv("MY_API_KEY");
    final String CLIENT_SECRET = System.getenv("MY_API_SECRET");
    Map<String, String> airlinesNames = new HashMap<>();
    Map<String, String> cityNames = new HashMap<>();

    @Override
    public String getAccessToken(){
        RestTemplate restTemplate = new RestTemplate();
        String requestBody = "grant_type=client_credentials" + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange("https://test.api.amadeus.com/v1/security/oauth2/token", HttpMethod.POST, request, Map.class);
        return (String) response.getBody().get("access_token");
    }

    @Override
    public ResponseEntity<Map> createURL(String specificURL) {
        String token = getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + token);
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(specificURL, HttpMethod.GET, entity, Map.class);
        return response;
    }

    @Override
    public List<AirportCode> getCodes(String keywordToSearch) {
        ResponseEntity<Map> response = createURL("https://test.api.amadeus.com/v1/reference-data/locations?subType=AIRPORT&keyword="+keywordToSearch+"&sort=analytics.travelers.score&view=LIGHT");
        List<Map<String, Object>> codeData = (List<Map<String, Object>>) response.getBody().get("data");
        List<AirportCode> result = new ArrayList<>();
        for(Map<String, Object> code: codeData){
            String codeToPrint = (String) code.get("iataCode");
            String name = (String) code.get("detailedName");
            AirportCode newCode = new AirportCode(name,codeToPrint);
            result.add(newCode);
        }
        return result;
    }

    @Override
    public List<Flight> getFlights(String departureAirportCode, String arrivalAirportCode, String departureDate, String arrivalDate, Integer adults, Boolean nonStop, String currency) {
        validateData(departureAirportCode, arrivalAirportCode, departureDate, arrivalDate, adults, nonStop, currency);
        StringBuilder builder = new StringBuilder("https://test.api.amadeus.com/v2/shopping/flight-offers?");
        builder.append("originLocationCode=").append(departureAirportCode).append("&destinationLocationCode=").append(arrivalAirportCode).
                append("&departureDate=").append(departureDate);
                if(!Objects.equals(arrivalDate, "none")){ //returnDate is optional
                    builder.append("&returnDate=").append(arrivalDate);
                }
                builder.append("&adults=").append(adults).
                append("&nonStop=").append(nonStop).append("&currencyCode=").append(currency).append("&max=150");
        ResponseEntity<Map> response  = createURL(builder.toString());
        List<Map<String, Object>> flightData = (List<Map<String, Object>>) response.getBody().get("data");
        Map<String, Object> dictionaries = ((Map<String, Object>) response.getBody().get("dictionaries"));

        List<Flight> flights = new ArrayList<>();
        Set<String> control = new HashSet<>();

        for(Map<String, Object> flight: flightData){
            Integer flightId = Integer.valueOf((String) flight.get("id"));

            //To check if we need to consider a returning date, in this case, returning segments
            int returningSements = 2;
            if(Objects.equals(arrivalDate, "none")){
                returningSements = 1;
            }

            //Perform cleaner code with this thing of itineraries
            Map<String, Object> checkItineraries = ((List<Map<String, Object>>) flight.get("itineraries")).get(0);
            List<Map<String, Object>> checkSegments = ((List<Map<String, Object>>) checkItineraries.get("segments"));
            Map<String, Object> check = (Map<String, Object>) checkSegments.get(0).get("departure");
            String checkTime = (String) check.get("at");

            if(control.contains(checkTime)) continue; //To not have the same flight with different id segments
            control.add(checkTime);

            List<Map<String, Object>> travelerPricings = ((List<Map<String, Object>>) flight.get("travelerPricings"));
            List<Map<String, Object>> fareDetailsBySegment = ((List<Map<String, Object>>) travelerPricings.get(0).get("fareDetailsBySegment"));
            Map<String, Object> dicAircrafts = ((Map<String, Object>) dictionaries.get("aircraft"));
            int segmentCount = 0;

            //I will move this to the repository layer to a separate method
            for(int i=0; i<returningSements;i++){
                List<Segments> totalSegments = new ArrayList<>();
                Map<String, Object> itineraries = ((List<Map<String, Object>>) flight.get("itineraries")).get(i);
                List<Map<String, Object>> allSegments = ((List<Map<String, Object>>) itineraries.get("segments"));

                for(Map<String, Object> segment: allSegments){
                    Map<String, Object> departure = (Map<String, Object>) segment.get("departure");
                    String departureTime = (String) departure.get("at");
                    String initialAirlineCode = (String) departure.get("iataCode");

                    Map<String, Object> arrival = (Map<String, Object>) segment.get("arrival");
                    String arrivalTime = (String) arrival.get("at");
                    String arriveAirlineCode = (String) arrival.get("iataCode");

                    String initialName = getCityName(initialAirlineCode);
                    String arriveName = getCityName(arriveAirlineCode);

                    Map<String, Object> operating = (Map<String, Object>) segment.get("operating");
                    String carrierCode;
                    if(operating == null)carrierCode = "";
                    else carrierCode = (String) operating.get("carrierCode");

                    Map<String, Object> aircraft = (Map<String, Object>) segment.get("aircraft");
                    //Maybe put the name of the Airline with the airline code here as well
                    String preCode = (String) aircraft.get("code") ;
                    String aircraftCode = (String) dicAircrafts.get(preCode);
                    String totalDuration = (String) segment.get("duration");

                    //Get total, fees and base
                    Map<String, Object> flightDetails = (Map<String, Object>) flight.get("price");
                    String total = (String) flightDetails.get("total");


                    List<Map<String, Object>> totalFees = ((List<Map<String, Object>>) flightDetails.get("fees"));
                    String fees = (String)totalFees.get(0).get("amount") + " " + (String)totalFees.get(0).get("type");
                    String base = (String) flightDetails.get("base");
                    FlightDetails finalFlightDetails = new FlightDetails(total, fees, base);

                    String segmentDetailsCabin = (String) fareDetailsBySegment.get(segmentCount).get("cabin");
                    String segmentDetailsClass = (String) fareDetailsBySegment.get(segmentCount).get("class");
                    List<Map<String, Object>> amenities = ((List<Map<String, Object>>) fareDetailsBySegment.get(segmentCount).get("amenities"));

                    List<Map<String,Boolean>> allAmenities = new ArrayList<>();
                    if(amenities != null){
                        for(Map<String, Object> amenity: amenities){
                            String description = (String) amenity.get("description");
                            Boolean isChargeable = (Boolean) amenity.get("isChargeable");
                            Map<String, Boolean> result = new HashMap<>();
                            result.put(description, isChargeable);
                            allAmenities.add(result);
                        }
                    }
                    segmentCount += 1;

                    Segments newSegment = new Segments(departureTime, arrivalTime, arriveName, initialName, initialAirlineCode,
                            arriveAirlineCode, carrierCode, aircraftCode, totalDuration,allAmenities, segmentDetailsClass, segmentDetailsCabin, finalFlightDetails);
                    totalSegments.add(newSegment);
                }

                String departureTime = totalSegments.get(0).getInitialDepartureDate();
                String arrivalTime = totalSegments.get(totalSegments.size()-1).getFinalArrivalDate();
                String carrierCode = totalSegments.get(0).getCarrierCode();

                Map<String, Object> carriers = ((Map<String, Object>) dictionaries.get("carriers"));
                String airlineName = (String) carriers.get(carrierCode);

                Map<String, Object> checkTotalTime = ((List<Map<String, Object>>) flight.get("itineraries")).get(i);
                String totalTime = (String) checkTotalTime.get("duration");

                Duration duration = Duration.parse(totalTime);
                LocalTime time = LocalTime.MIDNIGHT.plus(duration); //TO DO: Check the midnight

                Map<String, Object> price = (Map<String, Object>) flight.get("price");

                Float totalPrice = Float.valueOf((String) price.get("total"));
                Float pricePerTraveler = totalPrice/adults;
                String currencyName = (String) price.get("currency");

                Flight newFlight = new Flight(flightId, departureTime, arrivalTime, airlineName, carrierCode, time, totalPrice, pricePerTraveler, currencyName, totalSegments);
                flights.add(newFlight);
            }
        }
        flightRepository.cachedList = flights;
        return paginateFlights(flights, 0, 6);
    }

    //Check for 429 Too Many Requests error, I think is because we call it in less than a minute
    @Override
    public String getAirportName(String airportCode) {
        if(!airlinesNames.containsKey(airportCode)){
            StringBuilder builder = new StringBuilder("https://test.api.amadeus.com/v1/reference-data/airlines?airlineCodes=");
            System.out.println(airportCode);
            builder.append(airportCode);
            ResponseEntity<Map> response  = createURL(builder.toString());
            List<Map<String, Object>> codeData = (List<Map<String, Object>>) response.getBody().get("data");
            if(codeData.isEmpty()){
                airlinesNames.put(airportCode, ""); //To prevent the 429 too many requests when some code its not in the API
                return "";
            }            airlinesNames.put(airportCode, ((String) codeData.get(0).get("businessName")));
            return ((String) codeData.get(0).get("businessName"));
        }
        return airlinesNames.get(airportCode);
    }

    @Override
    public String getCityName(String airportCode) {
        String cityFromRepo = flightRepository.getCity(airportCode);
        if(!Objects.equals(cityFromRepo, "")) return cityFromRepo;
        if(!cityNames.containsKey(airportCode)){
            System.out.println(airportCode);
            StringBuilder builder = new StringBuilder("https://test.api.amadeus.com/v1/reference-data/locations?subType=CITY,AIRPORT&keyword="+airportCode);
            builder.append(airportCode);
            ResponseEntity<Map> response  = createURL(builder.toString());
            List<Map<String, Object>> codeData = (List<Map<String, Object>>) response.getBody().get("data");
            if(codeData.isEmpty()){
                cityNames.put(airportCode, ""); //To prevent the 429 too many requests when some code its not in the API
                return "";
            }
            Map<String, Object> cityName = (Map<String, Object>) codeData.get(0).get("address");
            cityNames.put(airportCode, ((String) cityName.get("cityName")));
            return ((String) cityName.get("cityName"));
        }
        return cityNames.get(airportCode);
    }

    @Override
    public List<Flight> sortFligths(Integer orderPrice, Integer orderDate) {
        List<Flight> cachedList = flightRepository.cachedList;

        if(orderPrice == 1 && orderDate == 1) return cachedList;
        if(orderPrice == 3 && orderDate ==1) {cachedList.sort(Comparator.comparingDouble(Flight::getTotalPrice));}
        else if(orderPrice ==2 && orderDate == 1) {cachedList.sort(Comparator.comparingDouble(Flight::getTotalPrice).reversed());}
        else if(orderPrice == 1 && orderDate == 3) {cachedList.sort(Comparator.comparing(Flight::getTotalTime, Comparator.nullsLast(LocalTime::compareTo)));}
        else if(orderPrice ==1 && orderDate ==2) {cachedList.sort(Comparator.comparing(Flight::getTotalTime, Comparator.nullsFirst(LocalTime::compareTo)).reversed());}
        else if(orderPrice == 2 && orderDate == 2){
            cachedList.sort(Comparator.comparing(Flight::getTotalPrice).reversed()
                    .thenComparing(Flight::getTotalTime, Comparator.nullsLast(LocalTime::compareTo)));
        }else if(orderPrice == 2 && orderDate == 3){
            cachedList.sort(Comparator.comparing(Flight::getTotalPrice)
                    .thenComparing(Flight::getTotalTime, Comparator.nullsFirst(LocalTime::compareTo)).reversed());
        }else if(orderPrice == 3 && orderDate ==2){
            cachedList.sort(Comparator.comparing(Flight::getTotalPrice).reversed()
                    .thenComparing(Flight::getTotalTime, Comparator.nullsFirst(LocalTime::compareTo)));
        }else if(orderPrice == 3 && orderDate == 3){
            cachedList.sort(Comparator.comparingDouble(Flight::getTotalPrice)
                    .thenComparing(Flight::getTotalTime, Comparator.nullsLast(LocalTime::compareTo).reversed()));
        }
        flightRepository.cachedList = cachedList;
//        FlightDetails flightDetails = new FlightDetails("-1", "-1", "1");
//        List<Map<String,Boolean>> amenities =  new ArrayList<Map<String, Boolean>>();
//        Segments segment = new Segments("-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1",amenities, "-1", "-1", flightDetails);
//        List<Segments> segments = new ArrayList<>(Arrays.asList(segment, segment));
//        Flight mockFlight = new Flight(-1, "-1", "-1", "-1", "-1", LocalTime.of(10,0), 1F, 1F, "-1", segments);
//        System.out.println(mockFlight);
//        cachedList.add(mockFlight);
        int size = cachedList.size();
        return cachedList.subList(0, 10);
    }

    @Override
    public void validateData(String departureAirportCode, String arrivalAirportCode, String departureDate, String arrivalDate, Integer adults, Boolean nonStop, String currency) {
        if(departureAirportCode == null || arrivalAirportCode == null){
            throw new ValidationException("Airport codes cannot be null");
        }
        if(departureDate == null){
            throw new ValidationException("Departure date cannot be null");
        }
        if(adults == null){
            throw new ValidationException("Adults cannot be null");
        }
        if(adults < 0 || adults > 9){
            throw new ValidationException("Adult value must be between 1 and 9");
        }
        if(currency == null){
            throw new ValidationException("Currency cannot be null");
        }
        if(nonStop == null){
            throw new ValidationException("Non stop cannot be null");
        }
        if(departureAirportCode.length() != 3 || arrivalAirportCode.length() !=3){
            throw new ValidationException("Airport codes must be of length 3");
        }

    }

    @Override
    public List<Flight> handlePagination(Integer pagination, Integer pageSize) {
        return  paginateFlights(flightRepository.cachedList, pagination, pageSize);
    }

    @Override
    public List<Flight> paginateFlights(List<Flight> flights, Integer pagination, Integer pageSize) {
        int totalFlights = flights.size();
        if (totalFlights < 6) return flights;

        int lowerBound = pagination*pageSize;
        int upperBound = (pagination+1)*pageSize;

        if(totalFlights < upperBound){
            upperBound = totalFlights;
        }

        flights = flights.subList(lowerBound, upperBound);
        return flights;
    }
}