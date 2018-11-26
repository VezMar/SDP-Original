package com.example.acadepsistemas.seguimiento.model;

public class Data {
    String observ;
    boolean status;

    public Data() {
    }

    public Data(String observ, boolean status) {
        this.observ = observ;
        this.status = status;
    }

    public String getObserv() {
        return observ;
    }

    public void setObserv(String observ) {
        this.observ = observ;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
