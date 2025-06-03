package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.dto.ChatMessageDTO;
import com.mytutors.mytutors.model.Conversacion;
import com.mytutors.mytutors.model.Mensaje;
import com.mytutors.mytutors.repository.ConversacionRepository;
import com.mytutors.mytutors.repository.MensajeRepository;
import com.mytutors.mytutors.service.ConversacionService;
import com.mytutors.mytutors.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private ConversacionRepository conversacionRepo;

    @Autowired
    private MensajeRepository mensajeRepo;

    @GetMapping("/{idConversacion}")
    public List<ChatMessageDTO> obtenerMensajes(@PathVariable Long idConversacion) {
        System.out.println(" Cargando mensajes de conversacion " + idConversacion);
        return mensajeService.obtenerMensajesConNombre(idConversacion);
    }

    @PostMapping
    public ResponseEntity<?> guardarMensaje(@RequestBody ChatMessageDTO dto) {
        Mensaje nuevo = new Mensaje();
        nuevo.setContenido(dto.getContenido());
        nuevo.setFechaEnvio(LocalDateTime.now());
        nuevo.setLeido(false);
        nuevo.setIdEmisor(dto.getIdEmisor());

        if (dto.getIdConversacion() != null) {
            Conversacion conv = conversacionRepo.findById(dto.getIdConversacion()).orElse(null);
            if (conv != null) {
                nuevo.setIdConversacion(conv.getId());
            }
        }

        mensajeRepo.save(nuevo);
        return ResponseEntity.ok().build();
    }

}

