package com.acadep.acadepsistemas.rsoencuel.model;

import java.util.List;

public class Activity {

    boolean active;
    String deleted;
    String description;
    String end;
    String id;
    List<Recursos> insumos;
    String project_id;
    String start;
    String subproject;
    String title;
    String updated_at;



    public Activity() {
    }

    public Activity(boolean active, String deleted, String description, String end, String id, List<Recursos> insumos, String project_id, String start, String subproject, String title, String updated_at) {
        this.active = active;
        this.deleted = deleted;
        this.description = description;
        this.end = end;
        this.id = id;
        this.insumos = insumos;
        this.project_id = project_id;
        this.start = start;
        this.subproject = subproject;
        this.title = title;
        this.updated_at = updated_at;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
