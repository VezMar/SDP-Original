package com.acadep.acadepsistemas.rso.model;

public class Foto {
    private String type;
    private String src;
    private Ubication ubicacion = new Ubication();
    private String created_at;

    public Foto() {
    }

    public Foto(String type, String src, Ubication ubicacion, String created_at) {
        this.type = type;
        this.src = src;
        this.ubicacion = ubicacion;
        this.created_at = created_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Ubication getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubication ubicacion) {
        this.ubicacion = ubicacion;
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
