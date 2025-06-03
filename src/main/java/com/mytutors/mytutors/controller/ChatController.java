package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.dto.ConversacionVistaDTO;
import com.mytutors.mytutors.model.Conversacion;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.service.ConversacionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/mis-conversaciones")
    @ResponseBody
    public List<ConversacionVistaDTO> obtenerConversaciones(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            System.out.println("Usuario en sesion: " + usuario.getNombre() + "(ID: "+ usuario.getId() + ")");
            List<ConversacionVistaDTO> conversaciones = conversacionService.obtenerConversaciones(usuario.getId());

            if (conversaciones != null && !conversaciones.isEmpty()) {
                System.out.println("conversaciones encontradas: " + conversaciones.size());
                conversaciones.forEach(conv -> System.out.println("Conversación: " + conv.getNombre()));
                return conversaciones;
            } else {
                System.out.println("No se encontraron conversaciones");
            }
        } else {
            System.out.println("Usuario no encontrado en sesión");
        }

        return Collections.emptyList();
    }


    @GetMapping("/tema/{idTema}")
    public String chatPorTema(@PathVariable Long idTema, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Conversacion conversacion = conversacionService.obtenerOCrearPorTema(idTema);

        if (conversacion != null) {
            model.addAttribute("conversacion", conversacion);
            model.addAttribute("usuario", usuario);
            return "chatTema"; // vista JSP del chat
        }
        return "redirect:/home";
    }

}
