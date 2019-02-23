package com.acadep.acadepsistemas.rso.model;

import java.util.List;

public class Data {

    String created_at;
    String description;
    String header;
    Total total;
    private boolean read;
    private Ref_event ref_event = new Ref_event();
    private double Lat;
    private double Lng;


    List<Foto> multimedia;
    List<Files> files;

    public Data() {
    }

    public Data(String created_at, String description, String header, Total total, boolean read, Ref_event ref_event, double lat, double lng, List<Foto> multimedia, List<Files> files) {
        this.created_at = created_at;
        this.description = description;
        this.header = header;
        this.total = total;
        this.read = read;
        this.ref_event = ref_event;
        Lat = lat;
        Lng = lng;
        this.multimedia = multimedia;
        this.files = files;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
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

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public Ref_event getRef_event() {
        return ref_event;
    }

    public void setRef_event(Ref_event ref_event) {
        this.ref_event = ref_event;
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

    public List<Foto> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Foto> multimedia) {
        this.multimedia = multimedia;
    }

    public List<Files> getFiles() {
        return files;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }
}
