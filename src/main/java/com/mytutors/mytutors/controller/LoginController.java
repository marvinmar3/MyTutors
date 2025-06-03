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

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //muestra el formlulario del login
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value="error", required=false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
        }
        return "login"; // login.jsp
    }

    //procesa el login
    @PostMapping("/login")
    public String procesarLogin(@RequestParam("correo") String correo,
                                @RequestParam("password") String password,
                                HttpSession session, Model model) {
        Usuario usuario = usuarioService.buscarPorCorreo(correo);

        if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
            session.setAttribute("usuario", usuario);
            session.setAttribute("nombreUsuario", usuario.getNombre());

            if("ADMIN".equals(usuario.getRolEnApp()))
            {
                return "redirect:/admin";
            }else{
                return "redirect:/home";
            }

        }else
        {
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            return "login";
        }
    }

    //ccerrar sesion
    @GetMapping("/logout")
    public String cerreSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
