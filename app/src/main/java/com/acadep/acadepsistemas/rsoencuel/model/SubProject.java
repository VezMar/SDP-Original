package com.acadep.acadepsistemas.rsoencuel.model;

public class SubProject {

    private String id;
    private String project_id;
    private String title;

    public SubProject() {
    }

    public SubProject(String id, String project_id, String title) {
        this.id = id;
        this.project_id = project_id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
