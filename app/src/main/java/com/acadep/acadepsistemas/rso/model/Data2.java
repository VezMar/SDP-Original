package com.acadep.acadepsistemas.rso.model;

import java.util.List;

public class Data2 {
    String observ;
    boolean during;
    private double Lat;
    private double Lng;
//    private List<String> Foto1;
//    private List<String> Foto2;
//    private List<String> Foto3;
//    private List<String> Foto4;
//    private List<String> Foto5;

    List<Foto> evidence;

    public Data2() {
    }


//    public Data2(String observ, boolean during, double lat, double lng) {
//        this.observ = observ;
//        this.during = during;
//        this.Lat = lat;
//        this.Lng = lng;
//    }


    public Data2(String observ, boolean during, double lat, double lng, List<Foto> evidence) {
        this.observ = observ;
        this.during = during;
        Lat = lat;
        Lng = lng;
        this.evidence = evidence;
    }

    public List<Foto> getEvidence() {
        return evidence;
    }

    public void setEvidence(List<Foto> evidence) {
        this.evidence = evidence;
    }


//    public Data2(String observ, boolean during, double lat, double lng, List<String> foto1, List<String> foto2, List<String> foto3, List<String> foto4, List<String> foto5) {
//        this.observ = observ;
//        this.during = during;
//        Lat = lat;
//        Lng = lng;
//        Foto1 = foto1;
//        Foto2 = foto2;
//        Foto3 = foto3;
//        Foto4 = foto4;
//        Foto5 = foto5;
//    }
//
//    public List<String> getFoto1() {
//        return Foto1;
//    }
//
//    public void setFoto1(List<String> foto1) {
//        Foto1 = foto1;
//    }
//
//    public List<String> getFoto2() {
//        return Foto2;
//    }
//
//    public void setFoto2(List<String> foto2) {
//        Foto2 = foto2;
//    }
//
//    public List<String> getFoto3() {
//        return Foto3;
//    }
//
//    public void setFoto3(List<String> foto3) {
//        Foto3 = foto3;
//    }
//
//    public List<String> getFoto4() {
//        return Foto4;
//    }
//
//    public void setFoto4(List<String> foto4) {
//        Foto4 = foto4;
//    }
//
//    public List<String> getFoto5() {
//        return Foto5;
//    }
//
//    public void setFoto5(List<String> foto5) {
//        Foto5 = foto5;
//    }

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
