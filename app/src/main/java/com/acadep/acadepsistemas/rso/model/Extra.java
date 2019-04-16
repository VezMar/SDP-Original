package com.acadep.acadepsistemas.rso.model;

import java.util.List;

public class Extra {

    String created_at;
    String description;
    String header;
    String event_id;
    String activity_id;
    private double Lat;
    private double Lng;

    List<Foto> multimedia;
    List<Files> files;

    public Extra() {
    }

    public Extra(String created_at, String description, String header, String event_id, String activity_id, double lat, double lng, List<Foto> multimedia, List<Files> files) {
        this.created_at = created_at;
        this.description = description;
        this.header = header;
        this.event_id = event_id;
        this.activity_id = activity_id;
        Lat = lat;
        Lng = lng;
        this.multimedia = multimedia;
        this.files = files;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public List<Files> getFiles() {
        return files;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
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

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
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
