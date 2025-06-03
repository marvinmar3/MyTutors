package com.mytutors.mytutors.controller;


import com.mytutors.mytutors.model.EstadoReporte;
import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.*;
import com.mytutors.mytutors.service.ReporteService;
import com.mytutors.mytutors.service.TemaService;
import com.mytutors.mytutors.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TemaRepository temaRepository;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private TemaService temaService;

    @Autowired
    private FacultadRepository facultadRepository;
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private CarreraRepository carreraRepository;


    @GetMapping
    public String panelAdmin(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if(usuario==null || !"ADMIN".equals(usuario.getRolEnApp())){
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        model.addAttribute("totalTutorias", temaRepository.count());
        model.addAttribute("totalReportes",0);


        return "admin";
    }

    @GetMapping("/tutorias")
    public String gestionarTutorias(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario==null || !"ADMIN".equalsIgnoreCase(usuario.getRolEnApp())){
            return "redirect:/login";
        }
        model.addAttribute("tutorias", temaRepository.findAll());
        return "admin/tutorias";
    }



    @GetMapping("/reportes")
    public String verReportesPendientes(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario==null || !"ADMIN".equalsIgnoreCase(usuario.getRolEnApp())){
            return "redirect:/login";
        }
        model.addAttribute("reportes", reporteService.obtenerPendientes());
        return "admin/reportes";
    }

    @PostMapping("/reportes/cambiarEstado")
    public String cambiarEstado(@RequestParam Long idReporte,
                                @RequestParam EstadoReporte nuevoEstado) {
        reporteService.cambiarEstado(idReporte, nuevoEstado);
        return "redirect:/admin/reportes";
    }

    @GetMapping("/usuarios")
    public String gestionarUsuarios(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario==null || !"ADMIN".equalsIgnoreCase(usuario.getRolEnApp())){
            return "redirect:/login";
        }
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/usuarios";
    }


    @PostMapping("/usuarios/cambiarEstado")
    public String cambiarEstadoUsuario(@RequestParam Long idUsuario,
                                       @RequestParam boolean activo) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario != null) {
            usuario.setActivo(activo);
            usuarioRepository.save(usuario);
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/tutorias/eliminar")
    public String eliminarTutoria(@RequestParam Long id) {
        temaRepository.deleteById(id);
        return "redirect:/admin/tutorias";
    }

}
