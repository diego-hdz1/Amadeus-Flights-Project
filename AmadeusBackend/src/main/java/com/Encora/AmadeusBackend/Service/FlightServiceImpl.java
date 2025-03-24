package com.Encora.AmadeusBackend.Service;

import com.Encora.AmadeusBackend.Model.AirportCode;
import com.Encora.AmadeusBackend.Model.Flight;
import com.Encora.AmadeusBackend.Model.Segments;
import com.Encora.AmadeusBackend.Repo.FlightRepo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightServiceImpl implements FlightService{

    @Autowired
    FlightRepo flightRepo;

  final String CLIENT_ID = System.getProperty("amadeus.client.id", System.getenv("AMADEUS_CLIENT_ID"));
  final String CLIENT_SECRET = System.getProperty("amadeus.client.secret", System.getenv("AMADEUS_CLIENT_SECRET"));
    
    Map<String, String> airlinesNames = new HashMap<>();
    Map<String, String> cityNames = new HashMap<>();

    //CHECAR
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


        //CHECAR ESTO COMPARADO CON EL DE CREATEURL
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
        //Se envia al repo y se ejecuta alla para obtener la info
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

        //HAY QUE CHECAR QUE PASA SI RETURN DATE ES NULL!!
        builder.append("originLocationCode=").append(departureAirportCode).append("&destinationLocationCode=").append(arrivalAirportCode).
                append("&departureDate=").append(departureDate).append("&returnDate=").append(arrivalDate).append("&adults=").append(adults).
                append("&nonStop=").append(nonStop).append("&currencyCode=").append(currency).append("&max=40");


        ResponseEntity<Map> response  = createURL(builder.toString());

        List<Map<String, Object>> flightData = (List<Map<String, Object>>) response.getBody().get("data");

        for(Map<String, Object> flight: flightData){
            Map<String, Object> itineraries = ((List<Map<String, Object>>) flight.get("travelerPricings")).get(0);
            System.out.println(itineraries);

        }


        List<Flight> flights = new ArrayList<>();
        for(Map<String, Object> flight: flightData){

            //CHECAR ESTO
            List<Segments> totalSegments = new ArrayList<>();

            //El itinerary uno es de ida y el otro es de regreo.
            //AHORITA COMO SOLO PONEMOS OBTENER EL 0 SE OBTIENEN SOLO LOS DE IDA
            Map<String, Object> itineraries = ((List<Map<String, Object>>) flight.get("itineraries")).get(0);

            //Aqui cada posicion es para llegar al destino, cada segmento esta entrelazado para llegar al destino

//            Map<String, Object> segment = ((List<Map<String, Object>>) itineraries.get("segments")).get(0);

            List<Map<String, Object>> allSegments = ((List<Map<String, Object>>) itineraries.get("segments"));


            //NO ESTA OBTENIENDO LOS DE REGRESO, solamente si se pone una return date
            //CADA VEZ QUE ESTOY CHECANDO UN VUELO VUELVE A EMPEZAR
            for(Map<String, Object> segment: allSegments){

                Map<String, Object> departure = (Map<String, Object>) segment.get("departure");
                String departureTime = (String) departure.get("at");
                String initialAirlineCode = (String) departure.get("iataCode");


                Map<String, Object> arrival = (Map<String, Object>) segment.get("arrival");
                String arrivalTime = (String) arrival.get("at");
                String arriveAirlineCode = (String) departure.get("iataCode");

                //CAMBIAR LOS CODIGOS A NOMBRES DE CIUDADES!

                //getCityName(initialAirlineCode)
                String initialName = "NO TODOS ESTAN?";
                String arriveName = "Otro";

                Map<String, Object> operating = (Map<String, Object>) segment.get("operating");

                String carrierCode = (String) operating.get("carrierCode");

                Map<String, Object> aircraft = (Map<String, Object>) segment.get("aircraft");
                String aircraftCode = (String) aircraft.get("code");


                String totalDuration = (String) segment.get("duration");

                Segments newSegment = new Segments(departureTime, arrivalTime, arriveName, initialName, initialAirlineCode, arriveAirlineCode, carrierCode, aircraftCode, totalDuration);
                totalSegments.add(newSegment);

                //System.out.println(newSegment.toString());
            }


            //CHECAR EL INDICE DE ESTAS MADRES, PARA QUE NO SEA ESTATICO, PORQUE LA LISTA ESTA FUERA DEL FOR
            //NO ESTA BIEN PORQUE HAY MAS SEGMENTOS POR VIAJE,
            String departureTime = totalSegments.get(0).getInitialDepartureDate();
            String arrivalTime = totalSegments.get(0).getFinalArrivalDate();
            String carrierCode = totalSegments.get(0).getCarrierCode();
            String airlineName = getAirportName(carrierCode);

            //FALTA ENCONTRAR EL NOMBRE Y EL TIEMPO

            //CAMBIAR A LOCALDATETIME???
            //HAY UNA PROPIEDAD EN LOS DATOS QUE DICE EL TIEMPO TOTAL ARRIBA DE LOS SEGMENTS
            //(String) itineraries.get("duration");
            Integer GenericTime = 10;
            //getTotalTime(departureTime, arrivalTime);

            Float pricePerTraveler = 10F; //ESTO LO CALCULAMOS NOSOTROS?? YO CREO QUE SI

            Map<String, Object> price = (Map<String, Object>) flight.get("price");

            Float totalPrice = Float.valueOf((String) price.get("total"));
            String currencyName = (String) price.get("currency");

            //SEGMENTS PEUDE TENER DISTINTOS, CHECAR ESO POR MIENTRAS ESTA ESTATICO
            Flight newFlight = new Flight(departureTime, arrivalTime, airlineName, carrierCode, GenericTime, totalPrice, pricePerTraveler, currencyName, totalSegments);
            flights.add(newFlight);
        }
        return flights;
    }

    @Override
    public String getAirportName(String airportCode) {
        if(!airlinesNames.containsKey(airportCode)){
            StringBuilder builder = new StringBuilder("https://test.api.amadeus.com/v1/reference-data/airlines?airlineCodes=");
            builder.append(airportCode);
            ResponseEntity<Map> response  = createURL(builder.toString());
            List<Map<String, Object>> codeData = (List<Map<String, Object>>) response.getBody().get("data");
            airlinesNames.put(airportCode, ((String) codeData.get(0).get("businessName")));
            return ((String) codeData.get(0).get("businessName"));
        }
        return airlinesNames.get(airportCode);
    }

    @Override
    public String getCityName(String airportCode) {
        if(!cityNames.containsKey(airportCode)){
            StringBuilder builder = new StringBuilder("https://test.api.amadeus.com/v1/reference-data/locations?subType=CITY,AIRPORT&keyword="+airportCode);
            builder.append(airportCode);
            ResponseEntity<Map> response  = createURL(builder.toString());

            List<Map<String, Object>> codeData = (List<Map<String, Object>>) response.getBody().get("data");
            System.out.println(codeData);
            Map<String, Object> cityName = (Map<String, Object>) codeData.get(0).get("address");


            cityNames.put(airportCode, ((String) cityName.get("cityName")));
            return ((String) cityName.get("cityName"));
        }
        return cityNames.get(airportCode);
    }

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
