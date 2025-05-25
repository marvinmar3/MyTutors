package com.mytutors.mytutors.model;


import jakarta.persistence.*;
//import java.time.LocalDateTime;

@Entity
@Table(name= "conversacion")
public class Conversacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String tipo;
    //private String LocalDateTime fecha_creado;

    @ManyToOne
    @JoinColumn(name = "id_tema")
    private Tema tema;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
}
