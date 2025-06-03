package com.mytutors.mytutors.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Entity
@Table(name= "participantes_conversacion")
@IdClass(ParticipanteConversacionId.class)
@Getter
@Setter
public class ParticipanteConversacion {

    @Id
    @Column(name = "id_conversacion")
    private Long idConversacion;

    @Id
    @Column(name = "id_usuario")
    private Long idUsuario;

}
