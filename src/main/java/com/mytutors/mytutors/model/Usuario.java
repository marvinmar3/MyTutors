package com.mytutors.mytutors.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;
    @Column(name= "contrasena")
    private String password;

    @ManyToOne
    @JoinColumn(name="id_facultad")
    private Facultad facultad;

    private String tipoUsuario;

    @ManyToOne
    @JoinColumn(name="id_carrera")
    private Carrera carrera;

    private String rolEnApp;

    @Column(name="ruta_foto")
    private String rutaFoto;

    @Column(nullable = false)
    private boolean activo= true;

    @ManyToMany(mappedBy = "participantes")
    private List<Conversacion> conversaciones = new ArrayList<>();


    //getters y setters

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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getRolEnApp() { return rolEnApp; }
    public void setRolEnApp(String rolEnApp) { this.rolEnApp = rolEnApp; }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Conversacion> getConversaciones() {
        return conversaciones;
    }

    public void setConversaciones(List<Conversacion> conversaciones) {
        this.conversaciones = conversaciones;
    }

    public boolean isActivo() {
        return activo;
    }
}
