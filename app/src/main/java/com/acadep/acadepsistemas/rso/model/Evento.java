package com.acadep.acadepsistemas.rso.model;

import android.provider.ContactsContract;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Evento {

    String user_id;
    String type_activity;
    String trabajador;
    String id;
    String name;
//    String start;
//    String end;
    String idactivity;
    String description;
    private Fecha end = new Fecha();
    private Fecha start = new Fecha();

    //  List<String> tools;

    List<Recursos> tools;
    List<Recursos> staff;

    boolean active;
    boolean deleted;

    public Evento(){}



//    public Evento(String user_id, String type_activity, String trabajador, String id, String name, String start, String end, String idactivity, String description, List<Recursos> tools, List<Recursos> staff, boolean active, boolean deleted) {
//        this.user_id = user_id;
//        this.type_activity = type_activity;
//        this.trabajador = trabajador;
//        this.id = id;
//        this.name = name;
//        this.start = start;
//        this.end = end;
//        this.idactivity = idactivity;
//        this.description = description;
//        this.tools = tools;
//        this.staff = staff;
//        this.active = active;
//        this.deleted = deleted;
//    }
//
//    public String getStart() {
//        return start;
//    }
//
//    public void setStart(String start) {
//        this.start = start;
//    }
//
//    public String getEnd() {
//        return end;
//    }
//
//    public void setEnd(String end) {
//        this.end = end;
//    }


    public Evento(String user_id, String type_activity, String trabajador, String id, String name, String idactivity, String description, Fecha end, Fecha start, List<Recursos> tools, List<Recursos> staff, boolean active, boolean deleted) {
        this.user_id = user_id;
        this.type_activity = type_activity;
        this.trabajador = trabajador;
        this.id = id;
        this.name = name;
        this.idactivity = idactivity;
        this.description = description;
        this.end = end;
        this.start = start;
        this.tools = tools;
        this.staff = staff;
        this.active = active;
        this.deleted = deleted;
    }

    public Fecha getEnd() {
        return end;
    }

    public void setEnd(Fecha end) {
        this.end = end;
    }

    public Fecha getStart() {
        return start;
    }

    public void setStart(Fecha start) {
        this.start = start;
    }



    public List<Recursos> getTools() {
        return tools;
    }

    public void setTools(List<Recursos> tools) {
        this.tools = tools;
    }

    public List<Recursos> getStaff() {
        return staff;
    }

    public void setStaff(List<Recursos> staff) {
        this.staff = staff;
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



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType_activity() {
        return type_activity;
    }

    public void setType_activity(String type_activity) {
        this.type_activity = type_activity;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(String trabajador) {
        this.trabajador = trabajador;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


