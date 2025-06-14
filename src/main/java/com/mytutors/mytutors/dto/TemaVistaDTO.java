package com.mytutors.mytutors.dto;

import com.mytutors.mytutors.model.Conversacion;
import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.model.Usuario;

public class TemaVistaDTO {
    private Tema tema;
    private Usuario tutor;
    private Usuario tutorado;
    private Conversacion conversacion;

    public TemaVistaDTO(Tema tema) {
        this.tema = tema;

        if ("tutorado".equalsIgnoreCase(tema.getRol())) {
            this.tutorado = tema.getCreador(); // el que creó es el tutorado
            this.tutor = tema.getTutor();      // puede ser null
        } else if ("tutor".equalsIgnoreCase(tema.getRol())) {
            this.tutor = tema.getTutor();           // el creador fue el tutor
            this.tutorado = tema.getCreador();      // se asigna al aceptar
        }

        System.out.println("Tema: " + tema.getNombre());
        System.out.println("Rol: " + tema.getRol());
        System.out.println("Tutor: " + (tutor != null ? tutor.getNombre() : "null"));
        System.out.println("Tutorado: " + (tutorado != null ? tutorado.getNombre() : "null"));
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public Usuario getTutor() {
        return tutor;
    }

    public void setTutor(Usuario tutor) {
        this.tutor = tutor;
    }

    public Usuario getTutorado() {
        return tutorado;
    }

    public void setTutorado(Usuario tutorado) {
        this.tutorado = tutorado;
    }

    public Conversacion getConversacion() {
        return conversacion;
    }

    public void setConversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
    }
}
