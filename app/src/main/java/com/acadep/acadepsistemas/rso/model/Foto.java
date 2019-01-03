package com.acadep.acadepsistemas.rso.model;

public class Foto {
    private String type;
    private String src;
    private Ubication ubicacion = new Ubication();
    private datetime datatime = new datetime();

    public Foto() {
    }

    public Foto(String type, String src, Ubication ubicacion, datetime datatime) {
        this.type = type;
        this.src = src;
        this.ubicacion = ubicacion;
        this.datatime = datatime;
    }

    public Ubication getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubication ubicacion) {
        this.ubicacion = ubicacion;
    }

    public datetime getDatatime() {
        return datatime;
    }

    public void setDatatime(datetime datatime) {
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
