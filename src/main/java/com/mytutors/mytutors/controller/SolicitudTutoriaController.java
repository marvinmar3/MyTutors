package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.service.SolicitudTutoriaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudTutoriaController {

    @Autowired
    private SolicitudTutoriaService solicitudService;

    @PostMapping("/enviar")
    public String enviarSolicitud(@RequestParam("idTema")Long idTema, HttpSession session, RedirectAttributes redirectAttributes){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null){
            return "redirect:/login";
        }
        try {
            solicitudService.crearSolicitud(usuario.getId(), idTema);
            redirectAttributes.addFlashAttribute("mensajeFlash", "✅ Solicitud enviada correctamente.");
        } catch (Exception e) {
            e.printStackTrace(); // Log en consola
            redirectAttributes.addFlashAttribute("mensajeFlash", "❌ Error al enviar solicitud.");
        }


        return "redirect:/home";
    }
}
