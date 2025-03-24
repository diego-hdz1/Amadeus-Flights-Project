package com.Encora.AmadeusBackend.Model;

public class Segments {
    private String initialDepartureDate;
    private String finalArrivalDate;

    //CHECAR ESTO
    private String initialCityName;
    private String arriveCityName;

    private String initialAirlineCode;
    private String arriveAirlineCode;

    //ESTO CAPAZ QUE VA EN DETAILS
    private String carrierCode;
    private String aircraft;
    private String totalDuration;

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

    public Segments(String initialDepartureDate, String finalArrivalDate, String arriveCityName, String initialCityName, String initialAirlineCode, String arriveAirlineCode, String carrierCode, String aircraft, String totalDuration) {
        this.initialDepartureDate = initialDepartureDate;
        this.finalArrivalDate = finalArrivalDate;
        this.arriveCityName = arriveCityName;
        this.initialCityName = initialCityName;
        this.initialAirlineCode = initialAirlineCode;
        this.arriveAirlineCode = arriveAirlineCode;
        this.carrierCode = carrierCode;
        this.aircraft = aircraft;
        this.totalDuration = totalDuration;
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
