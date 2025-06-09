package com.mytutors.mytutors.controller;


import com.mytutors.mytutors.dto.SolicitudNotificacionDTO;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.service.SolicitudTutoriaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
    // ✅ Usar estos nuevos:
    @PostMapping("/aceptar/{id}")
    public RedirectView aceptarSolicitud(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            solicitudService.aceptarSolicitud(id);
            redirectAttributes.addFlashAttribute("mensajeFlash", "✅ Solicitud aceptada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mensajeFlash", "❌ Error al aceptar solicitud.");
        }
        return new RedirectView("/home");
    }

    @PostMapping("/rechazar/{id}")
    public RedirectView rechazarSolicitud(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            solicitudService.rechazarSolicitud(id);
            redirectAttributes.addFlashAttribute("mensajeFlash", "❌ Solicitud rechazada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mensajeFlash", "❌ Error al rechazar solicitud.");
        }
        return new RedirectView("/home");
    }


}
