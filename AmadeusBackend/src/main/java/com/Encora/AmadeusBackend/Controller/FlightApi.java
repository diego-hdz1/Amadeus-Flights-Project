package com.Encora.AmadeusBackend.Controller;

import com.Encora.AmadeusBackend.Model.AirportCode;
import com.Encora.AmadeusBackend.Model.Flight;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FlightApi {


    @GetMapping("/codes")
    ResponseEntity<List<AirportCode>> getCodes(
            @RequestParam(required = false) String keyword
    );

    @GetMapping("/flights")
    ResponseEntity<List<Flight>> getFlights(
            @RequestParam(required = true) String departureAirportCode,
            @RequestParam(required = true) String arrivalAirportCode,
            @RequestParam(required = true) String departureDate,
            @RequestParam(required = false) String returnDate,
            @RequestParam(required = true) Integer adults,
            @RequestParam(required = true) Boolean nonStop,
            @RequestParam(required = false) String currencyCode
    );
}
