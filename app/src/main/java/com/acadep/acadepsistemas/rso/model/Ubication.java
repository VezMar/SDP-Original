package com.acadep.acadepsistemas.rso.model;

public class Ubication {
    private String lat;
    private String lng;

    public Ubication() {
    }

    public Ubication(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
