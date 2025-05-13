package com.mytutors.mytutors.model;


import jakarta.persistence.*;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Entity
@Table(name= "participantes_conversacion")
public class ParticipanteConversacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_conversacion")
    private Long idConversacion;

    @Column(name="id_usuario")
    private Long idUsuario;

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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
