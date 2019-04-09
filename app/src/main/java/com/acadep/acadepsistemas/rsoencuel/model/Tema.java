package com.acadep.acadepsistemas.rsoencuel.model;

public class Tema {

    String primary;
    String secundary;
    String tertiary;

    public Tema() {
    }

    public Tema(String primary, String secundary, String tertiary) {
        this.primary = primary;
        this.secundary = secundary;
        this.tertiary = tertiary;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getSecundary() {
        return secundary;
    }

    public void setSecundary(String secundary) {
        this.secundary = secundary;
    }

    public String getTertiary() {
        return tertiary;
    }

    public void setTertiary(String tertiary) {
        this.tertiary = tertiary;
    }
}
