package com.Encora.AmadeusBackend.Controller;

import com.Encora.AmadeusBackend.Model.AirportCode;
import com.Encora.AmadeusBackend.Model.Flight;
import com.Encora.AmadeusBackend.Service.FlightServiceImpl;
import com.Encora.AmadeusBackend.Service.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AmadeusControllerTest {

    @Mock
    private FlightServiceImpl flightService;

    @InjectMocks
    private FlightController flightController;

    @BeforeEach
    void setUp(){

    }

    @Test
    void testGetCodes(){
        String keyword = "NYC";
        List<AirportCode> mockCodes = Arrays.asList(new AirportCode("MockAirport 1","JFK"), new AirportCode("MockAirport 2","LGA"));
        when(flightService.getCodes(keyword)).thenReturn(mockCodes);

        ResponseEntity<List<AirportCode>> response = flightController.getCodes(keyword);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCodes, response.getBody());
    }

    @Test
    void testGetFlights(){
        List<Flight> mockFlights = Arrays.asList(new Flight(), new Flight());
        when(flightService.getFlights("JFK", "LAX", "2025-06-01", "2025-06-10", 1, true, "USD"))
                .thenReturn(mockFlights);

        ResponseEntity<List<Flight>> response = flightController.getFlights("JFK", "LAX", "2025-06-01", "2025-06-10", 1, true, "USD");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockFlights, response.getBody());
    }

    @Test
    void testSortFlights(){
        Integer orderPrice = 2;
        Integer orderDate = 1;

        List<Flight> sortedFlights = Arrays.asList(new Flight(), new Flight());
        when(flightService.sortFligths(orderPrice, orderDate)).thenReturn(sortedFlights);

        ResponseEntity<List<Flight>> response = flightController.sortFlights(orderPrice, orderDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sortedFlights, response.getBody());
    }

    @Test
    void testHandleException(){
        String error = "Invalid request";
        ValidationException exception = new ValidationException(error);

        ResponseEntity<String> response = flightController.handleException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(error, response.getBody());
    }


}
