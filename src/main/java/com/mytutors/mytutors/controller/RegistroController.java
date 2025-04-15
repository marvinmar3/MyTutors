package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.model.Facultad;
import com.mytutors.mytutors.model.Carrera;
import com.mytutors.mytutors.service.UsuarioService;
import com.mytutors.mytutors.repository.FacultadRepository;
import com.mytutors.mytutors.repository.CarreraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RegistroController {

    private final UsuarioService service;
    private final FacultadRepository facultadRepo;
    private final CarreraRepository carreraRepo;

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
    public String procesarRegistro(@ModelAttribute Usuario usuario, @RequestParam Long idCarrera) {
        service.registrar(usuario, idCarrera);
        return "redirect:/login";
    }

    @GetMapping("/carreras")
    @ResponseBody
    public List<Carrera> obtenerCarreras(@RequestParam("id_facultad") Long id) {
        Facultad facultad = facultadRepo.findById(id).orElse(null);
        return carreraRepo.findByFacultad(facultad);
    }
}
