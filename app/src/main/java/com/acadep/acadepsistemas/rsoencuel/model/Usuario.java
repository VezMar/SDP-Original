package com.acadep.acadepsistemas.rsoencuel.model;

public class Usuario {

    private String email;
    private String id;
    private String name;
    private role role;
    private String token;

    public Usuario(){

    }

    public Usuario(String email, String id, String name, com.acadep.acadepsistemas.rsoencuel.model.role role, String token) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.role = role;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.acadep.acadepsistemas.rsoencuel.model.role getRole() {
        return role;
    }

    public void setRole(com.acadep.acadepsistemas.rsoencuel.model.role role) {
        this.role = role;
    }
}
