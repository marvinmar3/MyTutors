package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;



@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping
    public String mostrarPerfil(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String correo = principal.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "perfil";
    }
    @PostMapping("/actualizar")
    public String actualizarPerfil(@ModelAttribute Usuario usuarioForm, @RequestParam("imagen") MultipartFile imagen,
                                   Principal principal,
                                   HttpServletRequest request)throws IOException {

        Usuario usuario = usuarioRepository.findByCorreo(principal.getName()).orElseThrow();

        usuario.setNombre(usuarioForm.getNombre());
        usuario.setFacultad(usuarioForm.getFacultad());
        usuario.setCarrera(usuarioForm.getCarrera());

        //guarda la imagen si se sube alguna
        if(!imagen.isEmpty())
        {
            String nombreArvhivo = "perfil_"+usuario.getId()+"_"+imagen.getOriginalFilename();
            String rutaBase= new ClassPathResource("static/img/usuarios/").getFile().getAbsolutePath();
            File destino = new File(rutaBase + File.separator + nombreArvhivo);
            imagen.transferTo(destino);
            usuario.setRutaFoto("/img/usuarios/"+nombreArvhivo);
        }
        // No actualizar el correo ni la contraseña aquí
        usuarioRepository.save(usuario);
        return "redirect:/perfil";
    }
}
