package com.mytutors.mytutors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tema;
    private String descripcion;
    private Double precio;
    private String rolDelCreador; // tutor o tutorado

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario creador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public String getRolDelCreador() {
        return rolDelCreador;
    }

    public void setRolDelCreador(String rolDelCreador) {
        this.rolDelCreador = rolDelCreador;
    }
}
