package com.Encora.AmadeusBackend.Service;

import com.Encora.AmadeusBackend.Model.AirportCode;
import com.Encora.AmadeusBackend.Model.Flight;
import com.Encora.AmadeusBackend.Model.FlightDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface FlightService {

    String getAccessToken();
    ResponseEntity<Map> createURL(String specificURL);
    List<AirportCode> getCodes(String keyword);
    List<Flight> getFlights(String departureAirportCode, String arrivalAirportCode, String departureDate, String arrivalDate, Integer adults, Boolean nonStop, String currency);
    String getAirportName(String airportCode);
    String getCityName(String airportCode);
    String getTotalTime(String initialTime, String finalTime);
    //METODO PARA CHECAR TODO, VERIFICAR Y LANZAR EXCEPCIONES



}
