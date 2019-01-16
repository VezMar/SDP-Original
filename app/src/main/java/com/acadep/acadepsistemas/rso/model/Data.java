package com.acadep.acadepsistemas.rso.model;

import java.util.ArrayList;
import java.util.List;

public class Data {

    String created_at;
    String description;
    String header;
    String event_id;
    private double Lat;
    private double Lng;
//    private List<String> Foto1;
//    private List<String> Foto2;
//    private List<String> Foto3;
//    private List<String> Foto4;
//    private List<String> Foto5;

    List<Foto> multimedia;
    //private String datetime;

    public Data() {
    }

    public Data(String created_at, String description, String header, String event_id, double lat, double lng, List<Foto> multimedia) {
        this.created_at = created_at;
        this.description = description;
        this.header = header;
        this.event_id = event_id;
        Lat = lat;
        Lng = lng;
        this.multimedia = multimedia;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public List<Foto> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Foto> multimedia) {
        this.multimedia = multimedia;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

}
