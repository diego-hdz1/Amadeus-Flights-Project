package com.Encora.AmadeusBackend.Controller;

import com.Encora.AmadeusBackend.Model.AirportCode;
import com.Encora.AmadeusBackend.Model.Flight;
import com.Encora.AmadeusBackend.Service.FlightServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class FlightController implements FlightApi{

    @Autowired
    FlightServiceImpl flightService;

    @Override
    public ResponseEntity<List<AirportCode>> getCodes(String keyword) {
        return new ResponseEntity<>(flightService.getCodes(keyword), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Flight>> getFlights(String departureAirportCode, String arrivalAirportCode, String departureDate,
                                                   String returnDate, Integer adults, Boolean nonStop, String currencyCode) {
        return new ResponseEntity<>(flightService.getFlights(departureAirportCode, arrivalAirportCode,departureDate,
                returnDate, adults, nonStop, currencyCode),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Flight>> sortFlights(Integer orderPrice, Integer orderDate) {
        return new ResponseEntity<>(flightService.sortFlighs(orderPrice, orderDate), HttpStatus.OK);
    }
}
