package com.Encora.AmadeusBackend.Repository;

import com.Encora.AmadeusBackend.AmadeusBackendApplication;
import com.Encora.AmadeusBackend.Model.Flight;
import com.Encora.AmadeusBackend.Repo.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = AmadeusBackendApplication.class)
public class AmadeusRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @BeforeEach
    void setUb(){
        flightRepository = new FlightRepository();
        Map<String, String> flights = new HashMap<>();
        flights.put("JFK", "Nueva York, EE.UU.");
    }

    @Test
    public void testGetCity(){
        String iataCODE = "JFK";
        assertEquals("Nueva York, EE.UU.", flightRepository.getCity(iataCODE));
    }

    @Test
    public void testGetCityNotFound(){
        String iataCODE = "SNL";
        assertEquals("", flightRepository.getCity(iataCODE));
    }

}
