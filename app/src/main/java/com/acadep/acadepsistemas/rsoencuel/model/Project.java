package com.acadep.acadepsistemas.rsoencuel.model;

public class Project {

    private boolean active;
//    private String deleted;
    private String description;
    private String end;
    private String id;
    private String start;
    private String title;




    public Project() {
    }

    public Project(boolean active, String description, String end, String id, String start, String title) {
        this.active = active;
        this.description = description;
        this.end = end;
        this.id = id;
        this.start = start;
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
