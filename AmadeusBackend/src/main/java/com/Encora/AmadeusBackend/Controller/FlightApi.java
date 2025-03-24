package com.Encora.AmadeusBackend.Controller;

import com.Encora.AmadeusBackend.Model.AirportCode;
import com.Encora.AmadeusBackend.Model.Flight;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FlightApi {

    //Se hacen los mappings del frontend con este backend.
    //Despues con este backend en el service se le habla a la API amadeus


    //GET para los codigos
    //GET para buscar los vuelos dependiendo de los parametros que me enviaron
    //GET con id para ver especificamente los datos del vuelo
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

    //PARA LOS DETAILS COMO SERA LA CONSULTA??


}
