package com.mytutors.mytutors.controller;


import com.mytutors.mytutors.model.ChatMessage;
import com.mytutors.mytutors.model.Mensaje;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.MensajeRepository;
import com.mytutors.mytutors.repository.UsuarioRepository;
import com.mytutors.mytutors.service.MensajeService;
import com.mytutors.mytutors.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import com.mytutors.mytutors.dto.ChatMessageDTO;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.mytutors.mytutors.service.MensajeService;



@Controller
public class ChatWebSocketController {


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MensajeService mensajeService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;


    @MessageMapping("/enviar")
    public void procesarMensaje(@Payload ChatMessageDTO mensaje) {
        Mensaje entidad = new Mensaje();
        entidad.setIdConversacion(mensaje.getIdConversacion());
        entidad.setIdEmisor(mensaje.getIdEmisor());
        entidad.setContenido(mensaje.getContenido());
        entidad.setFechaEnvio(mensaje.getFechaEnvio());
        entidad.setLeido(false);

        Mensaje guardado = mensajeService.guardar(entidad);

        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setIdConversacion(guardado.getIdConversacion());
        dto.setIdEmisor(guardado.getIdEmisor());
        dto.setContenido(guardado.getContenido());
        dto.setFechaEnvio(guardado.getFechaEnvio());

        String nombre = usuarioService.obtenerNombrePorId(guardado.getIdEmisor());
        System.out.println("Nombre del emisor (WebSocket): " + nombre);
        dto.setNombreEmisor(nombre);

        simpMessagingTemplate.convertAndSend("/topic/mensajes/" + mensaje.getIdConversacion(), dto);
    }




}

