package com.acadep.acadepsistemas.rso.model;

import java.util.List;

public class Activity {

    String deleted;
    String description;
    String end;
    String id;
    List<Recursos> insumos;
    String project_id;
    String start;
    String subproject;
    String title;
    List<String> users;


    public Activity() {
    }

    public Activity(String deleted, String description, String end, String id, List<Recursos> insumos, String project_id, String start, String subproject, String title, List<String> users) {
        this.deleted = deleted;
        this.description = description;
        this.end = end;
        this.id = id;
        this.insumos = insumos;
        this.project_id = project_id;
        this.start = start;
        this.subproject = subproject;
        this.title = title;
        this.users = users;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
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

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<Recursos> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<Recursos> insumos) {
        this.insumos = insumos;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getSubproject() {
        return subproject;
    }

    public void setSubproject(String subproject) {
        this.subproject = subproject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
