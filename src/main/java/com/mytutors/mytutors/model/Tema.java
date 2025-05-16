package com.mytutors.mytutors.model;

import jakarta.persistence.*;

@Entity
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

    private String rol;

    @ManyToOne
    @JoinColumn(name="id_materia")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name="id_tutor")
    private Usuario tutor;

    @ManyToOne
    @JoinColumn(name = "id_creador")
    private Usuario creador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public Usuario getTutor() {
        return tutor;
    }
    public void setTutor(Usuario tutor) {
        this.tutor = tutor;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
