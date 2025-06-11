package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.model.Facultad;
import com.mytutors.mytutors.model.Carrera;
import com.mytutors.mytutors.service.UsuarioService;
import com.mytutors.mytutors.repository.FacultadRepository;
import com.mytutors.mytutors.repository.CarreraRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class RegistroController {

    @Autowired
    private final UsuarioService service;
    @Autowired
    private final FacultadRepository facultadRepo;
    @Autowired
    private final CarreraRepository carreraRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    public RegistroController(UsuarioService service, FacultadRepository facultadRepo, CarreraRepository carreraRepo) {
        this.service = service;
        this.facultadRepo = facultadRepo;
        this.carreraRepo = carreraRepo;
    }

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        List<Facultad> facultades = facultadRepo.findAll();
        System.out.println("Facultades diponibles: " + facultades.size());
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("facultades", facultades);
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute Usuario usuario,
                                   @RequestParam("idCarrera") Long idCarrera,
                                   HttpSession session,
                                   Model model) {

        //primero verifica si ya existe el correo
        if(service.existeCorreo(usuario.getCorreo())){
            List<Facultad> facultades = facultadRepo.findAll();
            model.addAttribute("facultades", facultades);
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "El correo ya está registrado. Intenta con otro.");
            return "registro";
        }
        String correo = usuario.getCorreo();

        //validacion ddel correo
        if(correo.endsWith("@estudiantes.uv.mx"))
        {
            usuario.setTipoUsuario("estudiante");
        }else if(correo.endsWith("@uv.mx")){
            usuario.setTipoUsuario("profesor");
        } else {
            //si no es institucional, regresamos a registro con error
            List<Facultad> facultades = facultadRepo.findAll();
            model.addAttribute("facultades", facultades);
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "Correo inválido, debe ser institucional.");
            return "registro";
        }
        // Guardar el usuario (con carrera y facultad)
        service.registrar(usuario, idCarrera);

        // Guardamos el usuario en sesión
        session.setAttribute("usuario", usuario);

        // También datos al modelo para la vista
        model.addAttribute("nombreUsuario", usuario.getNombre());
        model.addAttribute("rolEnApp", usuario.getRolEnApp());

        return "redirect:/home"; // Muestra home.jsp
    }

    @GetMapping("/carreras")
    @ResponseBody
    public List<Carrera> obtenerCarreras(@RequestParam("id_facultad") Long id) {
        Facultad facultad = facultadRepo.findById(id).orElse(null);
        return carreraRepo.findByFacultad(facultad);
    }
}
