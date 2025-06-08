package com.mytutors.mytutors.controller;


import com.mytutors.mytutors.dto.SolicitudNotificacionDTO;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.service.SolicitudTutoriaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {
    @Autowired
    private SolicitudTutoriaService solicitudService;

    //obtener solicitudes pendientes para el usuario actual
    @GetMapping
    public List<SolicitudNotificacionDTO> obtenerSolicitudes(HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null){
            return List.of(); //vacio si no hay sesion
        }
        return solicitudService.obtenerSolicitudesDTO(usuario.getId());
    }

    //aceptar una solicitud
    @PostMapping("/aceptar/{id}")
    public String aceptarSolicitud(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        solicitudService.aceptarSolicitud(id);
        redirectAttributes.addFlashAttribute("mensajeFlash", "✅ Solicitud aceptada.");
        return "redirect:/home";
    }

    @PostMapping("/rechazar/{id}")
    public String rechazarSolicitud(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        solicitudService.rechazarSolicitud(id);
        redirectAttributes.addFlashAttribute("mensajeFlash", "❌ Solicitud rechazada.");
        return "redirect:/home";
    }

}
