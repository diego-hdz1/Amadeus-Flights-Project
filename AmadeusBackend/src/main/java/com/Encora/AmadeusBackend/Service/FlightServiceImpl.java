package com.Encora.AmadeusBackend.Service;

import com.Encora.AmadeusBackend.Model.AirportCode;
import com.Encora.AmadeusBackend.Model.Flight;
import com.Encora.AmadeusBackend.Model.Segments;
import com.Encora.AmadeusBackend.Repo.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FlightServiceImpl implements FlightService{

    @Autowired
    FlightRepository flightRepository;

  final String CLIENT_ID = System.getProperty("amadeus.client.id", System.getenv("AMADEUS_CLIENT_ID"));
  final String CLIENT_SECRET = System.getProperty("amadeus.client.secret", System.getenv("AMADEUS_CLIENT_SECRET"));

    Map<String, String> airlinesNames = new HashMap<>();
    Map<String, String> cityNames = new HashMap<>();
    Set<String> control = new HashSet<>();

    //TO DO: Implement it on a validation method
    public FlightServiceImpl() throws Exception {
    }

    @Override
    public String getAccessToken(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);


        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity("https://test.api.amadeus.com/v1/security/oauth2/token", request, Map.class);
        return (String) response.getBody().get("access_token");
    }

    @Override
    public ResponseEntity<Map> createURL(String specificURL) {
        String token = getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(specificURL, HttpMethod.GET, entity, Map.class);
        return response;
    }

    @Override
    public List<AirportCode> getCodes(String keywordToSearch) {
        ResponseEntity<Map> response = createURL("https://test.api.amadeus.com/v1/reference-data/locations?subType=CITY,AIRPORT&keyword="+keywordToSearch);
        List<Map<String, Object>> codeData = (List<Map<String, Object>>) response.getBody().get("data");
        List<AirportCode> result = new ArrayList<>();
        for(Map<String, Object> code: codeData){
            String codeToPrint = (String) code.get("id");
            String name = (String) code.get("detailedName");
            AirportCode newCode = new AirportCode(name,codeToPrint);
            result.add(newCode);
            System.out.println(newCode.toString());
        }
        return result;
    }

    @Override
    public List<Flight> getFlights(String departureAirportCode, String arrivalAirportCode, String departureDate, String arrivalDate, Integer adults, Boolean nonStop, String currency) {
        StringBuilder builder = new StringBuilder("https://test.api.amadeus.com/v2/shopping/flight-offers?");
        builder.append("originLocationCode=").append(departureAirportCode).append("&destinationLocationCode=").append(arrivalAirportCode).
                append("&departureDate=").append(departureDate);
                if(!Objects.equals(arrivalDate, "none")){ //returnDate is optional
                    builder.append("&returnDate=").append(arrivalDate);
                }
                builder.append("&adults=").append(adults).
                append("&nonStop=").append(nonStop).append("&currencyCode=").append(currency).append("&max=200");

        ResponseEntity<Map> response  = createURL(builder.toString());
        List<Map<String, Object>> flightData = (List<Map<String, Object>>) response.getBody().get("data");
        List<Flight> flights = new ArrayList<>();

        for(Map<String, Object> flight: flightData){
            List<Segments> totalSegments = new ArrayList<>();
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


            //I will move this to the repository layer to a separate method
            for(int i=0; i<returningSements;i++){
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

                    //EL OPERATING A VECES ES NULL???
                    String carrierCode;
                    if(operating == null)carrierCode = "";
                    else carrierCode = (String) operating.get("carrierCode");

                    Map<String, Object> aircraft = (Map<String, Object>) segment.get("aircraft");
                    String aircraftCode = (String) aircraft.get("code");
                    String totalDuration = (String) segment.get("duration");
                    Segments newSegment = new Segments(departureTime, arrivalTime, arriveName, initialName, initialAirlineCode, arriveAirlineCode, carrierCode, aircraftCode, totalDuration);
                    totalSegments.add(newSegment);
                }
            }
            String departureTime = totalSegments.get(0).getInitialDepartureDate();

            //I need to get the final arrival date but without considering the return segments
            String arrivalTime = totalSegments.get(0).getFinalArrivalDate();
            String carrierCode = totalSegments.get(0).getCarrierCode();

            //String airlineName = getAirportName(carrierCode);
            String airlineName = "prueba";

            String totalTime = (String) checkItineraries.get("duration");
            Map<String, Object> price = (Map<String, Object>) flight.get("price");

            Float totalPrice = Float.valueOf((String) price.get("total"));
            Float pricePerTraveler = totalPrice/adults;
            String currencyName = (String) price.get("currency");

            Flight newFlight = new Flight(flightId, departureTime, arrivalTime, airlineName, carrierCode, totalTime, totalPrice, pricePerTraveler, currencyName, totalSegments);
            flights.add(newFlight);
        }
        return flights;
    }

    //Check for 429 Too Many Requests error
    @Override
    public String getAirportName(String airportCode) {
        if(!airlinesNames.containsKey(airportCode)){
            StringBuilder builder = new StringBuilder("https://test.api.amadeus.com/v1/reference-data/airlines?airlineCodes=");
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

    //Check for 429 Too Many Requests error
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

    //Maybe I will not use this method afterall
    @Override
    public String getTotalTime(String initialTime, String finalTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime time1 = LocalDateTime.parse(initialTime, formatter);
        LocalDateTime time2 = LocalDateTime.parse(finalTime, formatter);

        Duration duration = Duration.between(time1, time2);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        System.out.println("Hora: " + hours + " Minutos: " + minutes);
        return "";
    }
}
