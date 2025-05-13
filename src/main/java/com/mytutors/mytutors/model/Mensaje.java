package com.mytutors.mytutors.model;


import jakarta.persistence.*;

@Entity
@Table(name = "mensaje")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_conversacion")
    private Long idConversacion;

    @Column(name = "id_emisor")
    private Long idEmisor;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "leido")
    private Boolean leido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdConversacion() {
        return idConversacion;
    }

    public void setIdConversacion(Long idConversacion) {
        this.idConversacion = idConversacion;
    }

    public Long getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(Long idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Boolean getLeido() {
        return leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }
}
