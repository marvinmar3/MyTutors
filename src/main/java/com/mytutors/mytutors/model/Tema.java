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
    @JoinColumn(name="id_materia", insertable=false, updatable=false)
    private Materia materia;

    @ManyToOne
    @JoinColumn(name="id_tutor", insertable=false, updatable=false )
    private Usuario tutor;

    @ManyToOne
    @JoinColumn(name = "id_creador", insertable = false, updatable = false)
    private Usuario creador; //tutorado

    @Column(name = "id_creador")
    private Long idCreador;

    @Column(name = "id_tutor")
    private Long idTutor;

    @Column(name = "id_materia")
    private Long idMateria;

    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public Long getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(Long idTutor) {
        this.idTutor = idTutor;
    }

    public Long getIdCreador() {
        return idCreador;
    }

    public void setIdCreador(Long idCreador) {
        this.idCreador = idCreador;
    }

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

    public Usuario getCreador() {
        return creador;
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

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    public void setCreador(Usuario creador) {
        this.creador = creador;
    }
}
