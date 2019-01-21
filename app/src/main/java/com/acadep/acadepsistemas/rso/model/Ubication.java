package com.acadep.acadepsistemas.rso.model;

public class Ubication {
    private double lat;
    private double lng;

    public Ubication() {
    }

    public Ubication(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
