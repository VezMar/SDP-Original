package com.acadep.acadepsistemas.rso.model;

public class datetime {

    private String date;
    private String time;


    public datetime() {
    }

    public datetime(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}