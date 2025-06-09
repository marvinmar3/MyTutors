package com.mytutors.mytutors.dto;

import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.model.Usuario;

public class TemaVistaDTO {
    private Tema tema;
    private Usuario tutor;
    private Usuario tutorado;

    public TemaVistaDTO(Tema tema) {
        this.tema = tema;

        if ("tutorado".equalsIgnoreCase(tema.getRol())) {
            this.tutorado = tema.getCreador(); // el que creó es el tutorado
            this.tutor = tema.getTutor();      // puede ser null
        } else if ("tutor".equalsIgnoreCase(tema.getRol())) {
            this.tutor = tema.getTutor();           // el creador fue el tutor
            this.tutorado = tema.getCreador();      // se asigna al aceptar
        }
    }

    public Tema getTema() {
        return tema;
    }

    public Usuario getTutor() {
        return tutor;
    }

    public Usuario getTutorado() {
        return tutorado;
    }

}
