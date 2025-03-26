package com.Encora.AmadeusBackend.Model;

public class AirportCode {
    private String detailedName;
    private String airportCode;
    //PONER MAS DATOS???

    public AirportCode(String detailedName, String airportCode) {
        this.detailedName = detailedName;
        this.airportCode = airportCode;
    }

    public String getDetailedName() {
        return detailedName;
    }

    public void setDetailedName(String detailedName) {
        this.detailedName = detailedName;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    @Override
    public String toString() {
        return "AirportCode{" +
                "detailedName='" + detailedName + '\'' +
                ", airportCode='" + airportCode + '\'' +
                '}';
    }
}
