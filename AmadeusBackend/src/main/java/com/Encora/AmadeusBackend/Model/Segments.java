package com.Encora.AmadeusBackend.Model;

import java.util.List;
import java.util.Map;

public class Segments {
    private String initialDepartureDate;
    private String finalArrivalDate;
    private String initialCityName;
    private String arriveCityName;
    private String initialAirlineCode;
    private String arriveAirlineCode;

    //Maybe this should be in the details
    private String carrierCode;
    private String aircraft;
    private String totalDuration;
    private List<Map<String,Boolean>> amenities;
    private String classNumber;
    private String cabin;
    private FlightDetails flightDetails;

    @Override
    public String toString() {
        return "Segments{" +
                "initialDepartureDate='" + initialDepartureDate + '\'' +
                ", finalArrivalDate='" + finalArrivalDate + '\'' +
                ", initialCityName='" + initialCityName + '\'' +
                ", arriveCityName='" + arriveCityName + '\'' +
                ", initialAirlineCode='" + initialAirlineCode + '\'' +
                ", arriveAirlineCode='" + arriveAirlineCode + '\'' +
                ", carrierCode='" + carrierCode + '\'' +
                ", aircraft='" + aircraft + '\'' +
                ", totalDuration='" + totalDuration + '\'' +
                '}';
    }

    public Segments(String initialDepartureDate, String finalArrivalDate, String arriveCityName, String initialCityName, String initialAirlineCode, String arriveAirlineCode, String carrierCode, String aircraft, String totalDuration
    ,List<Map<String,Boolean>> amenities, String classNumber, String cabin, FlightDetails flightDetails) {
        this.initialDepartureDate = initialDepartureDate;
        this.finalArrivalDate = finalArrivalDate;
        this.arriveCityName = arriveCityName;
        this.initialCityName = initialCityName;
        this.initialAirlineCode = initialAirlineCode;
        this.arriveAirlineCode = arriveAirlineCode;
        this.carrierCode = carrierCode;
        this.aircraft = aircraft;
        this.totalDuration = totalDuration;
        this.amenities = amenities;
        this.classNumber = classNumber;
        this.cabin = cabin;
        this.flightDetails = flightDetails;
    }

    public FlightDetails getFlightDetails() {
        return flightDetails;
    }

    public void setFlightDetails(FlightDetails flightDetails) {
        this.flightDetails = flightDetails;
    }

    public List<Map<String, Boolean>> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Map<String, Boolean>> amenities) {
        this.amenities = amenities;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getInitialDepartureDate() {
        return initialDepartureDate;
    }

    public void setInitialDepartureDate(String initialDepartureDate) {
        this.initialDepartureDate = initialDepartureDate;
    }

    public String getInitialCityName() {
        return initialCityName;
    }

    public void setInitialCityName(String initialCityName) {
        this.initialCityName = initialCityName;
    }

    public String getFinalArrivalDate() {
        return finalArrivalDate;
    }

    public void setFinalArrivalDate(String finalArrivalDate) {
        this.finalArrivalDate = finalArrivalDate;
    }

    public String getArriveCityName() {
        return arriveCityName;
    }

    public void setArriveCityName(String arriveCityName) {
        this.arriveCityName = arriveCityName;
    }

    public String getInitialAirlineCode() {
        return initialAirlineCode;
    }

    public void setInitialAirlineCode(String initialAirlineCode) {
        this.initialAirlineCode = initialAirlineCode;
    }

    public String getArriveAirlineCode() {
        return arriveAirlineCode;
    }

    public void setArriveAirlineCode(String arriveAirlineCode) {
        this.arriveAirlineCode = arriveAirlineCode;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }
}
