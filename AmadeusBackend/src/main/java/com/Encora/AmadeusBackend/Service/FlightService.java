package com.Encora.AmadeusBackend.Service;

import com.Encora.AmadeusBackend.Model.AirportCode;
import com.Encora.AmadeusBackend.Model.Flight;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface FlightService {

    String getAccessToken();
    ResponseEntity<Map> getData(String specificURL);
    List<AirportCode> getCodes(String keyword);
    List<Flight> getFlights(String departureAirportCode, String arrivalAirportCode, String departureDate, String arrivalDate, Integer adults, Boolean nonStop, String currency);
    String getAirportName(String airportCode);
    String getCityName(String airportCode);
    List<Flight> sortFligths(Integer orderPrice, Integer orderDate);
    void validateData(String departureAirportCode, String arrivalAirportCode, String departureDate, String arrivalDate, Integer adults, Boolean nonStop, String currency);
    List<Flight> handlePagination(Integer pagination, Integer pageSize);
    List<Flight> paginateFlights(List<Flight> flights, Integer pagination, Integer pageSize);
}
