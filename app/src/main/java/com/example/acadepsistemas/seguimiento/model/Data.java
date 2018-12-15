package com.example.acadepsistemas.seguimiento.model;

public class Data {
    String observ;
    boolean before;
    private double Lat;
    private double Lng;

    public Data() {
    }


    public Data(String observ, boolean before, double lat, double lng) {
        this.observ = observ;
        this.before = before;
        this.Lat = lat;
        this.Lng = lng;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }

    public String getObserv() {
        return observ;
    }

    public void setObserv(String observ) {
        this.observ = observ;
    }

    public boolean isBefore() {
        return before;
    }

    public void setBefore(boolean before) {
        this.before = before;
    }
}
