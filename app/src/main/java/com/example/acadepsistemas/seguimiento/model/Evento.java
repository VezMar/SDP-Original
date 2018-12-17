package com.example.acadepsistemas.seguimiento.model;

import android.app.LauncherActivity;

import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Evento {
    String user_id;
    String actividad;
    String trabajador;
    String idevent;
    String name;
    String start;
    String end;
    String idactivity;
    String description;
    List<String> tools;
    boolean active;
    boolean deleted;

    public Evento(){}


    /*public Evento(String user_id, String actividad, String trabajador, String idevent, String name, String start, String end, String idactivity, String description, boolean active) {
        this.user_id = user_id;
        this.actividad = actividad;
        this.trabajador = trabajador;
        this.idevent = idevent;
        this.name = name;
        this.start = start;
        this.end = end;
        this.idactivity = idactivity;
        this.description = description;
        this.active = active;
    }*/


   /* public Evento(String user_id, String actividad, String trabajador, String idevent, String name, String start, String end, String idactivity, String description, boolean active, boolean deleted) {
        this.user_id = user_id;
        this.actividad = actividad;
        this.trabajador = trabajador;
        this.idevent = idevent;
        this.name = name;
        this.start = start;
        this.end = end;
        this.idactivity = idactivity;
        this.description = description;
        this.active = active;
        this.deleted = deleted;
    }*/

    public Evento(String user_id, String actividad, String trabajador, String idevent, String name, String start, String end, String idactivity, String description, List<String> tools, boolean active, boolean deleted) {
        this.user_id = user_id;
        this.actividad = actividad;
        this.trabajador = trabajador;
        this.idevent = idevent;
        this.name = name;
        this.start = start;
        this.end = end;
        this.idactivity = idactivity;
        this.description = description;
        this.tools = tools;
        this.active = active;
        this.deleted = deleted;
    }

    public List<String> getTools() {
        return tools;
    }

    public void setTools(List<String> tools) {
        this.tools = tools;
    }

    public String getIdactivity() {
        return idactivity;
    }

    public void setIdactivity(String idactivity) {
        this.idactivity = idactivity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(String trabajador) {
        this.trabajador = trabajador;
    }

    public String getIdevent() {
        return idevent;
    }

    public void setIdevent(String idevent) {
        this.idevent = idevent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}


