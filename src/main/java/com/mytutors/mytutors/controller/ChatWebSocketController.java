package com.mytutors.mytutors.controller;


import com.mytutors.mytutors.model.Mensaje;
import com.mytutors.mytutors.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {
    @Autowired
    private MensajeService mensajeService;

    @MessageMapping("/chat.privado")
    @SendTo("/topic/mensajes/{idConversacion}")
    public Mensaje enviarMensaje(Mensaje mensaje){
        //guardar el mensaje en la base de datos
        mensajeService.guardarMensaje(mensaje);
        return mensaje;
    }
}
