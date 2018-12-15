package com.example.acadepsistemas.seguimiento.model;

public class Data3 {
    String observ;
    boolean after;
    private double Lat;
    private double Lng;

    public Data3() {
    }


    public Data3(String observ, boolean after, double lat, double lng) {
        this.observ = observ;
        this.after = after;
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

    public boolean isAfter() {
        return after;
    }

    public void setAfter(boolean after) {
        this.after = after;
    }
}
