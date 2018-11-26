package com.example.acadepsistemas.seguimiento.model;

public class Evento {
    String uid;
    String actividad;
    String trabajador;
    String idevent;

    public Evento(){

    }


    public Evento(String uid, String actividad, String trabajador, String idevent) {
        this.uid = uid;
        this.actividad = actividad;
        this.trabajador = trabajador;
        this.idevent = idevent;
    }

    public String getIdevent() {
        return idevent;
    }

    public void setIdevent(String idevent) {
        this.idevent = idevent;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(String trabajador) {
        this.trabajador = trabajador;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
}
