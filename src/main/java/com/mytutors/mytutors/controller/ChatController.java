package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.dto.ConversacionVistaDTO;
import com.mytutors.mytutors.model.Conversacion;
import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.TemaRepository;
import com.mytutors.mytutors.service.ConversacionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Controller
public class ChatController {
    @Autowired
    private  ConversacionService conversacionService;
    @Autowired
    private TemaRepository temaRepository;

    @GetMapping("/mis-conversaciones")
    @ResponseBody
    public List<ConversacionVistaDTO> obtenerConversaciones(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            System.out.println("Usuario no encontrado en sesión");
            return Collections.emptyList();
        }

        List<ConversacionVistaDTO> conversaciones = conversacionService.obtenerConversaciones(usuario.getId());

        if (conversaciones.isEmpty()) {
            System.out.println("No se encontraron conversaciones para el usuario " + usuario.getNombre());
        } else {
            System.out.println("✅ " + conversaciones.size() + " conversaciones encontradas");
            conversaciones.forEach(conv -> System.out.println("🗨️ " + conv.getNombre()));
        }

        return conversaciones;
    }


    @GetMapping("/api/conversacion/por-tema/{idTema}")
    @ResponseBody
    public ResponseEntity<ConversacionVistaDTO> obtenerConversacionPorTema(@PathVariable Long idTema, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Tema tema = temaRepository.findById(idTema).orElse(null);
        if (tema == null || tema.getCreador() == null || tema.getTutor() == null)
            return ResponseEntity.notFound().build();

        if (!usuario.getId().equals(tema.getCreador().getId()) && !usuario.getId().equals(tema.getTutor().getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Conversacion conversacion = conversacionService.obtenerOCrearConversacionIndividual(tema);
        if (conversacion == null) return ResponseEntity.internalServerError().build();

        ConversacionVistaDTO dto = new ConversacionVistaDTO();
        dto.setId(conversacion.getId());
        dto.setNombre("Chat con " + (
                usuario.getId().equals(tema.getTutor().getId()) ?
                        tema.getCreador().getNombre() :
                        tema.getTutor().getNombre()
        ));

        return ResponseEntity.ok(dto);
    }




}
