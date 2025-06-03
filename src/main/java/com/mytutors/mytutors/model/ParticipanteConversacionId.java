package com.mytutors.mytutors.model;

import java.io.Serializable;
import java.util.Objects;

public class ParticipanteConversacionId implements Serializable {
    private Long idConversacion;
    private Long idUsuario;

    public ParticipanteConversacionId() {}

    public ParticipanteConversacionId(Long idConversacion, Long idUsuario) {
        this.idConversacion = idConversacion;
        this.idUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if(this==o) return true;
        if(!(o instanceof ParticipanteConversacionId)) return false;
        ParticipanteConversacionId that = (ParticipanteConversacionId) o;
        return Objects.equals(idConversacion, that.idConversacion) && Objects.equals(idUsuario, that.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConversacion, idUsuario);
    }
}
