package com.Encora.AmadeusBackend.Service;

import com.Encora.AmadeusBackend.Model.AirportCode;
import com.Encora.AmadeusBackend.Model.Flight;
import com.Encora.AmadeusBackend.Repo.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AmadeusServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FlightServiceImpl flightService;

    @BeforeEach
    void setUp(){
        flightService = new FlightServiceImpl();
        flightService.flightRepository = flightRepository;
    }

    @Test
    void testGetAccessToken(){
        Map<String, String> response = new HashMap<>();
        response.put("access_token", "mocked_token");

        ResponseEntity<Map> mockResponse = new ResponseEntity<>(response, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(Map.class))).thenReturn(mockResponse);

        String token = flightService.getAccessToken();
        assertEquals("mocked_token", token);
    }

    @Test
    void testCreateURL(){
        String testURL = "https://test.api.amadeus.com/v1/test";
        Map<String, String> response = new HashMap<>();
        ResponseEntity<Map> mockResponse = ResponseEntity.ok(response);

        when(restTemplate.exchange(eq(testURL), eq(HttpMethod.GET), any(), eq(Map.class))).thenReturn(mockResponse);
        ResponseEntity<Map> finalResponse = flightService.getData(testURL);
        assertEquals(mockResponse, finalResponse);
    }

    @Test
    void testGetCodes(){
        Map<String, Object> responseMap = new HashMap<>();
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> airportData = new HashMap<>();

        airportData.put("iataCode", "JFK");
        airportData.put("detailedName", "John F Kennedy");
        dataList.add(airportData);
        responseMap.put("data",dataList);

        ResponseEntity<Map> mockResponses = ResponseEntity.ok(responseMap);
        List<AirportCode> result = flightService.getCodes("JFK");

        assertFalse(result.isEmpty());
    }

    @Test
    void testValidateDate(){
        assertThrows(ValidationException.class, ()-> flightService.validateData(null, "JFK", "2025-07-21", "none", 1, true, "MXN"));
        assertThrows(ValidationException.class, ()-> flightService.validateData("JFK",null, "2025-07-21", "none", 1, true, "MXN"));
        assertThrows(ValidationException.class, ()-> flightService.validateData( "JFK", "SYD", null , "none", 1, true, "MXN"));
        assertThrows(ValidationException.class, ()-> flightService.validateData( "JFK", "SYD", "2025-07-21" , "2025-07-21", null, true, "MXN"));
        assertThrows(ValidationException.class, ()-> flightService.validateData( "JFK", "SYD", "2025-07-21" , "2025-07-21", 1, null, "MXN"));
        assertThrows(ValidationException.class, ()-> flightService.validateData( "JFK", "SYD", "2025-07-21" , "2025-07-21", 1, true, null));
    }

    @Test
    void testPaginateFlights(){
        List<Flight> flights = new ArrayList<>();
        for(int i =0; i<20; i++){
            flights.add(new Flight());
        }
        List<Flight> result = flightService.paginateFlights(flights, 1, 5);
        assertEquals(5, result.size());
    }

    @Test
    void testSortFlightsPrice(){
        Flight f1 = new Flight();
        f1.setTotalPrice(200F);
        Flight f2 = new Flight();
        f2.setTotalPrice(100F);
        List<Flight> flights = Arrays.asList(f1, f2);
        flightRepository.cachedList = flights;
        List<Flight> sortedFlights = flightService.sortFligths(3,1);
        assertEquals(100F, sortedFlights.get(0).getTotalPrice());
    }

    @Test
    void testSortFlightsDate(){
        Flight f1 = new Flight();
        f1.setTotalTime(LocalTime.of(11, 41));
        Flight f2 = new Flight();
        f1.setTotalTime(LocalTime.of(12, 12));
        List<Flight> flights = Arrays.asList(f1, f2);
        flightRepository.cachedList = flights;
        List<Flight> sortedFlights = flightService.sortFligths(1,3);
        assertEquals(LocalTime.of(12,12), sortedFlights.get(0).getTotalTime());
    }

}
