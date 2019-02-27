package com.acadep.acadepsistemas.rso.model;

import java.util.ArrayList;

public class Project {

    private String deleted;
    private String description;
    private String end;
    private String id;
    private String start;
    private ArrayList<String> subprojects;
    private String title;




    public Project() {
    }

    public Project(String deleted, String description, String end, String id, String start, ArrayList<String> subprojects, String title) {
        this.deleted = deleted;
        this.description = description;
        this.end = end;
        this.id = id;
        this.start = start;
        this.subprojects = subprojects;
        this.title = title;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public ArrayList<String> getSubprojects() {
        return subprojects;
    }

    public void setSubprojects(ArrayList<String> subprojects) {
        this.subprojects = subprojects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
