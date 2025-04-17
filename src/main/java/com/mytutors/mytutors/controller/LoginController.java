package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("correo") String correo,
                                @RequestParam("password") String password,
                                HttpSession session,
                                Model model) {

        //validacion manual del correo
        if(!correo.endsWith("@estudiantes.uv.mx"))
        {
            model.addAttribute("error", "Correo no válido.");
            return "login";
        }


        Usuario usuario = usuarioService.buscarPorCorreo(correo);

        //validacion de usuario y contraseña
        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            session.setAttribute("usuario", usuario);
            model.addAttribute("nombreUsuario", usuario.getNombre());
            model.addAttribute("tipoUsuario", usuario.getTipoUsuario());
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login"; // login.jsp
        }
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login"; // login.jsp
    }

}
