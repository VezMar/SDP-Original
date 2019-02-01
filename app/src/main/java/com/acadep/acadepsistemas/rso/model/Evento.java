package com.acadep.acadepsistemas.rso.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Evento {

    boolean active;
    String activity_id;
    int advanced;
    boolean deleted;
    String description;
    String end;
    String id;
    List<Recursos> staff;
    String start;
    int status;
    String title;
    List<Recursos> tools;
    Total total = new Total();
    String type;
    String type_activity;
    String user_id;


//    private datetime end = new datetime();
//    private datetime start = new datetime();

    //  List<String> tools;


    public Evento(){}

    public Evento(boolean active, String activity_id, int advanced, boolean deleted, String description, String end, String id, List<Recursos> staff, String start, int status, String title, List<Recursos> tools, Total total, String type, String type_activity, String user_id) {
        this.active = active;
        this.activity_id = activity_id;
        this.advanced = advanced;
        this.deleted = deleted;
        this.description = description;
        this.end = end;
        this.id = id;
        this.staff = staff;
        this.start = start;
        this.status = status;
        this.title = title;
        this.tools = tools;
        this.total = total;
        this.type = type;
        this.type_activity = type_activity;
        this.user_id = user_id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public int getAdvanced() {
        return advanced;
    }

    public void setAdvanced(int advanced) {
        this.advanced = advanced;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Recursos> getStaff() {
        return staff;
    }

    public void setStaff(List<Recursos> staff) {
        this.staff = staff;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Recursos> getTools() {
        return tools;
    }

    public void setTools(List<Recursos> tools) {
        this.tools = tools;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_activity() {
        return type_activity;
    }

    public void setType_activity(String type_activity) {
        this.type_activity = type_activity;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}


