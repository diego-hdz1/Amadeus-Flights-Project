package com.Encora.AmadeusBackend.Model;

import java.time.LocalTime;
import java.util.List;

public class Flight {

    private Integer flightId;
    private String initialDepartureDate;
    private String finalArrivalDate;
    private String airlineName;
    private String airlineCode;
    //The name and code of the operating airline (only if different from the main airline)

    //the total time of the flight from departure to arrival. This should include the flight time, layover time of all segments (if there are any)
    private LocalTime totalTime;
    private Float totalPrice;
    private Float pricePerTraveler;
    private String currency;
    private List<Segments> segments;

    public Flight(Integer flightId, String initialDepartureDate, String finalArrivalDate, String airlineName, String airlineCode, LocalTime totalTime, Float totalPrice, Float pricePerTraveler, String currency, List<Segments> segments) {
        this.flightId = flightId;
        this.initialDepartureDate = initialDepartureDate;
        this.finalArrivalDate = finalArrivalDate;
        this.airlineName = airlineName;
        this.airlineCode = airlineCode;
        this.totalTime = totalTime;
        this.totalPrice = totalPrice;
        this.pricePerTraveler = pricePerTraveler;
        this.currency = currency;
        this.segments = segments;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightId=" + flightId +
                ", initialDepartureDate='" + initialDepartureDate + '\'' +
                ", finalArrivalDate='" + finalArrivalDate + '\'' +
                ", airlineName='" + airlineName + '\'' +
                ", airlineCode='" + airlineCode + '\'' +
                ", totalTime=" + totalTime +
                ", totalPrice=" + totalPrice +
                ", pricePerTraveler=" + pricePerTraveler +
                ", currency='" + currency + '\'' +
                ", segments=" + segments +
                '}';
    }

    public List<Segments> getSegments() {
        return segments;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public void setSegments(List<Segments> segments) {
        this.segments = segments;
    }

    public String getInitialDepartureDate() {
        return initialDepartureDate;
    }

    public void setInitialDepartureDate(String initialDepartureDate) {
        this.initialDepartureDate = initialDepartureDate;
    }

    public String getFinalArrivalDate() {
        return finalArrivalDate;
    }

    public void setFinalArrivalDate(String finalArrivalDate) {
        this.finalArrivalDate = finalArrivalDate;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public LocalTime getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(LocalTime totalTime) {
        this.totalTime = totalTime;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Float getPricePerTraveler() {
        return pricePerTraveler;
    }

    public void setPricePerTraveler(Float pricePerTraveler) {
        this.pricePerTraveler = pricePerTraveler;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
