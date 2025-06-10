package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.dto.ChatMessageDTO;
import com.mytutors.mytutors.model.Conversacion;
import com.mytutors.mytutors.model.Mensaje;
import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.ConversacionRepository;
import com.mytutors.mytutors.repository.MensajeRepository;
import com.mytutors.mytutors.repository.UsuarioRepository;
import com.mytutors.mytutors.service.ConversacionService;
import com.mytutors.mytutors.service.MensajeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private ConversacionRepository conversacionRepo;

    @Autowired
    private MensajeRepository mensajeRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/{idConversacion}")
    public ResponseEntity<?> obtenerMensajes(@PathVariable Long idConversacion, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Conversacion conv =conversacionRepo.findById(idConversacion).orElse(null);
        if(conv==null)return ResponseEntity.notFound().build();
        Tema tema= conv.getTema();

        Long idCreador = tema.getCreador() != null ? tema.getCreador().getId() : null;
        Long idTutor = tema.getTutor() != null ? tema.getTutor().getId() : null;

        if (!Objects.equals(usuario.getId(), idCreador) && !Objects.equals(usuario.getId(), idTutor)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes acceso a esta conversación");
        }

        return ResponseEntity.ok(mensajeService.obtenerMensajesConNombre(idConversacion));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> guardarMensaje(@RequestBody ChatMessageDTO dto) {
        System.out.println("DTO recibido: " + dto);
        if (dto.getIdConversacion() == null) {
            return ResponseEntity.badRequest().body("❌ El ID de conversación es obligatorio");
        }

        Optional<Conversacion> optionalConv = conversacionRepo.findById(dto.getIdConversacion());
        if (optionalConv.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ La conversación no existe");
        }

        Mensaje nuevo = new Mensaje();
        nuevo.setContenido(dto.getContenido());
        nuevo.setFechaEnvio(LocalDateTime.now());
        nuevo.setLeido(false);
        nuevo.setIdEmisor(dto.getIdEmisor());
        nuevo.setIdConversacion(dto.getIdConversacion());

        mensajeRepo.save(nuevo);

        ChatMessageDTO enviado = new ChatMessageDTO();
        enviado.setIdConversacion(nuevo.getIdConversacion());
        enviado.setIdEmisor(nuevo.getIdEmisor());
        enviado.setContenido(dto.getContenido());
        enviado.setFechaEnvio(nuevo.getFechaEnvio());

        Optional<Usuario> emisor = usuarioRepository.findById(nuevo.getIdEmisor());
        enviado.setNombreEmisor(emisor.map(Usuario::getNombre).orElse("Desconocido"));

        messagingTemplate.convertAndSend("/topic/mensajes/" + dto.getIdConversacion(), enviado);

        return ResponseEntity.ok().build();
    }


}

