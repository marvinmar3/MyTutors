package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
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

    public HomeController(UsuarioService usuarioService, TemaService temaService) {
        this.usuarioService = usuarioService;
        this.temaService = temaService;
    }

    //@GetMapping("/")
    /*public String index()
    {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal){
        String correo = principal.getName();
        Usuario usuario = usuarioService.buscarPorCorreo(correo);

        model.addAttribute("nombreUsuario", usuario.getNombre());
        model.addAttribute("tipoUsuario", usuario.getTipoUsuario());

        return "home";
    }*/
    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        Usuario usuario= (Usuario) session.getAttribute("usuario");

        model.addAttribute("usuario", usuario);
        model.addAttribute("nombreUsuario", usuario!=null?usuario.getNombre():"Invitado");
        model.addAttribute("temas", temaService.obtenerTemas());

        List<Tema> temas = temaService.obtenerTemas();
        return "home";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
