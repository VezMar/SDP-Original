package com.example.acadepsistemas.seguimiento.model;

public class Data {
    String observ;
    boolean status;
    double Lat;
    double Lng;

    public Data(String observation, boolean statuss, double longitude) {
    }


    public Data(String observ, boolean status, double lat, double lng) {
        this.observ = observ;
        this.status = status;
        this.Lat = lat;
        this.Lng = lng;
    }

    public String getObserv() {
        return observ;
    }

    public void setObserv(String observ) {
        this.observ = observ;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
