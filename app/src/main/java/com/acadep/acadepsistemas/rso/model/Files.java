package com.acadep.acadepsistemas.rso.model;

public class Files {

    private String created_at;
    private String name;
    private String src;
    private String type;

    public Files() {
    }

    public Files(String created_at, String name, String src, String type) {
        this.created_at = created_at;
        this.name = name;
        this.src = src;
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
