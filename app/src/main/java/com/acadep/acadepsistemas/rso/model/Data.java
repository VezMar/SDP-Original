package com.acadep.acadepsistemas.rso.model;

import java.util.ArrayList;
import java.util.List;

public class Data {

    String observ;
    boolean before;
    private double Lat;
    private double Lng;
//    private List<String> Foto1;
//    private List<String> Foto2;
//    private List<String> Foto3;
//    private List<String> Foto4;
//    private List<String> Foto5;

    List<Foto> evidence;
    //private String datetime;

    public Data() {
    }


    /*public Data(String observ, boolean before, double lat, double lng) {
        this.observ = observ;
        this.before = before;
        this.Lat = lat;
        this.Lng = lng;
    }*/

    public Data(String observ, boolean before, double lat, double lng, List<Foto> evidence) {
        this.observ = observ;
        this.before = before;
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


//        public Data(String observ, boolean before, double lat, double lng, List<String> foto1, List<String> foto2, List<String> foto3, List<String> foto4, List<String> foto5) {
//        this.observ = observ;
//        this.before = before;
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

    public boolean isBefore() {
        return before;
    }

    public void setBefore(boolean before) {
        this.before = before;
    }
}
