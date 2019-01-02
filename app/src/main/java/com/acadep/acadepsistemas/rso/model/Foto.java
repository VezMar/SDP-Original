package com.acadep.acadepsistemas.rso.model;

public class Foto {
    private String type;
    private String src;
    private Ubicacion ubicacion = new Ubicacion();
    private datatime datatime = new datatime();

    public Foto() {
    }

    public Foto(String type, String src, Ubicacion ubicacion, com.acadep.acadepsistemas.rso.model.datatime datatime) {
        this.type = type;
        this.src = src;
        this.ubicacion = ubicacion;
        this.datatime = datatime;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public com.acadep.acadepsistemas.rso.model.datatime getDatatime() {
        return datatime;
    }

    public void setDatatime(com.acadep.acadepsistemas.rso.model.datatime datatime) {
        this.datatime = datatime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
