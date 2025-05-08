package com.mytutors.mytutors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import com.mytutors.mytutors.model.ChatMessage; // Ajusta si lo colocaste en otro paquete


@Controller
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/topic/mensajes")
    public ChatMessage enviarMensaje(ChatMessage mensaje)
    {
        return mensaje;
    }
}
