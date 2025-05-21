package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.CarreraRepository;
import com.mytutors.mytutors.repository.FacultadRepository;
import com.mytutors.mytutors.repository.MateriaRepository;
import com.mytutors.mytutors.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.util.List;
import com.mytutors.mytutors.service.TemaService;
import com.mytutors.mytutors.model.Tema;

import java.security.Principal;

@Controller
public class HomeController {
    private final UsuarioService usuarioService;
    private final TemaService temaService;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    public HomeController(UsuarioService usuarioService, TemaService temaService) {
        this.usuarioService = usuarioService;
        this.temaService = temaService;
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        Usuario usuario= (Usuario) session.getAttribute("usuario");

        if(usuario==null){
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("materias", materiaRepository.findAll());
        model.addAttribute("nombreUsuario", usuario!=null?usuario.getNombre():"Invitado");
        model.addAttribute("temas", temaService.obtenerTemas());

        model.addAttribute("carreras", carreraRepository.findAll());
        model.addAttribute("facultades", facultadRepository.findAll());

        //List<Tema> temas = temaService.obtenerTemas();
        return "home";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
