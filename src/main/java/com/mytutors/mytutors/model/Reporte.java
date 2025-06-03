package com.mytutors.mytutors.model;


import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import com.mytutors.mytutors.model.EstadoReporte;

import java.time.LocalDateTime;

@Entity
@Table(name = "reporte")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable= false)
    private String motivo;

    @Column(length=1000)
    private String descripcion;

    @Column(nullable=false)
    private LocalDateTime fechaCreado;

    @Enumerated(EnumType.STRING)
    private EstadoReporte estado = EstadoReporte.PENDIENTE;

    @ManyToOne
    @JoinColumn(name = "id_emisor")
    private Usuario emisor;

    @ManyToOne
    @JoinColumn(name = "id_tema", nullable= true)
    private Tema temaReportado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(LocalDateTime fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public EstadoReporte getEstado() {
        return estado;
    }

    public void setEstado(EstadoReporte estado) {
        this.estado = estado;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public Tema getTemaReportado() {
        return temaReportado;
    }

    public void setTemaReportado(Tema temaReportado) {
        this.temaReportado = temaReportado;
    }
}

