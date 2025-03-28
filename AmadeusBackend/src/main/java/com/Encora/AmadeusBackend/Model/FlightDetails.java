package com.Encora.AmadeusBackend.Model;

public class FlightDetails {
    private String total;
    private String fees;
    private String base;

    @Override
    public String toString() {
        return "FlightDetails{" +
                "total='" + total + '\'' +
                ", fees='" + fees + '\'' +
                ", base='" + base + '\'' +
                '}';
    }

    public FlightDetails(String total, String fees, String base) {
        this.total = total;
        this.fees = fees;
        this.base = base;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
