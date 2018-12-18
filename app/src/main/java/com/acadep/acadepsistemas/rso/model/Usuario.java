package com.acadep.acadepsistemas.rso.model;

public class Usuario {

    private String activo;
    private String apellido;
    private String correo;
    private String nombre;
    private String rol;
    private String uid;


    public Usuario(){

    }

    public Usuario(String activo, String apellido, String correo, String nombre, String rol, String uid) {
        this.activo = activo;
        this.apellido = apellido;
        this.correo = correo;
        this.nombre = nombre;
        this.rol = rol;
        this.uid = uid;
    }

    // ------------------------------------


    @Override
    public String toString() {
        return "Usuario{" +
                "activo='" + activo + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", rol='" + rol + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    //-----------------------------------
    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
