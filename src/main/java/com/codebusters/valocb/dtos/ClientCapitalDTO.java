package com.codebusters.valocb.dtos;

public class ClientCapitalDTO {

    private String clientName;

    private double capital;

    public ClientCapitalDTO(String clientName, double capital) {
        this.clientName = clientName;
        this.capital = capital;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }
}
