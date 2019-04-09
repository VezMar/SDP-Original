package com.acadep.acadepsistemas.rsoencuel.model;

public class Total {

    int number;
    String unit;

    public Total() {
    }

    public Total(int number, String unit) {
        this.number = number;
        this.unit = unit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
