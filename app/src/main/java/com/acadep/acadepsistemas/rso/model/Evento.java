package com.acadep.acadepsistemas.rso.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Evento {

    boolean active;
    String activity_id;
    String activity_name;
    int advanced;
    boolean before_complete;
    String deleted;
    String description;
    boolean during_complete;
    String end;
    String id;
    List<Recursos> staff;
    String start;
    String color;
    String subproject_name;
    String title;
    List<Recursos> tools;
    Total total = new Total();
    String type;
    String type_activity;
    String user_id;


//    private datetime end = new datetime();
//    private datetime start = new datetime();

    //  List<String> tools;


    public Evento() {
    }

    public Evento(boolean active, String activity_id, String activity_name, int advanced, boolean before_complete, String deleted, String description, boolean during_complete, String end, String id, List<Recursos> staff, String start, String status, String subproject_name, String title, List<Recursos> tools, Total total, String type, String type_activity, String user_id) {
        this.active = active;
        this.activity_id = activity_id;
        this.activity_name = activity_name;
        this.advanced = advanced;
        this.before_complete = before_complete;
        this.deleted = deleted;
        this.description = description;
        this.during_complete = during_complete;
        this.end = end;
        this.id = id;
        this.staff = staff;
        this.start = start;
        this.color = status;
        this.subproject_name = subproject_name;
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

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public int getAdvanced() {
        return advanced;
    }

    public void setAdvanced(int advanced) {
        this.advanced = advanced;
    }

    public boolean isBefore_complete() {
        return before_complete;
    }

    public void setBefore_complete(boolean before_complete) {
        this.before_complete = before_complete;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDuring_complete() {
        return during_complete;
    }

    public void setDuring_complete(boolean during_complete) {
        this.during_complete = during_complete;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSubproject_name() {
        return subproject_name;
    }

    public void setSubproject_name(String subproject_name) {
        this.subproject_name = subproject_name;
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


