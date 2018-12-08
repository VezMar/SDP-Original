package com.example.acadepsistemas.seguimiento.model;

public class Data2 {
    String observ;
    boolean during;
    double Lat;
    double Lng;

    public Data2() {
    }


    public Data2(String observ, boolean during, double lat, double lng) {
        this.observ = observ;
        this.during = during;
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

    public boolean isDuring() {
        return during;
    }

    public void setDuring(boolean during) {
        this.during = during;
    }


}
