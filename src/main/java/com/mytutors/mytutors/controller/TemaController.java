package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.MateriaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.model.Materia;
import com.mytutors.mytutors.repository.TemaRepository;
import java.util.List;
import org.springframework.ui.Model;
import com.mytutors.mytutors.service.TemaService;
import com.mytutors.mytutors.repository.UsuarioRepository;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/temas")
public class TemaController {

    @Autowired
    private TemaRepository temaRepository;
    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private TemaService temaService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping("/crear")
    public String crearTema(@RequestParam String nombre,
                            @RequestParam String descripcion,
                            @RequestParam Long idMateria,
                            @RequestParam String rol,
                            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Materia materia = materiaRepository.findById(idMateria).orElse(null);

        if (usuario != null && materia != null) {
            Tema tema = new Tema();
            tema.setNombre(nombre);
            tema.setDescripcion(descripcion);
            tema.setMateria(materia);
            tema.setRol(rol);
            tema.setTutor(usuario); // o tutorado, depende de cómo manejes esto

            temaRepository.save(tema);
        }

        return "redirect:/home";
    }

    @GetMapping("/filtrar")
    public String filtrarTemas(@RequestParam(required = false) Long idMateria,
                               @RequestParam(required = false) Long idTutor,
                               @RequestParam(required = false) String rol,
                               @RequestParam(required = false) Boolean sinTutor,
                               @RequestParam(required = false) String texto,
                               Model model) {

        List<Tema> temasFiltrados;

        if (texto != null && !texto.isEmpty()) {
            temasFiltrados = temaService.buscarPorTexto(texto);
        } else if (Boolean.TRUE.equals(sinTutor)) {
            temasFiltrados = temaService.buscarSinTutor();
        } else if (idMateria != null) {
            temasFiltrados = temaService.buscarPorMateria(idMateria);
        } else if (idTutor != null) {
            temasFiltrados = temaService.buscarPorTutor(idTutor);
        } else if (rol != null) {
            temasFiltrados = temaService.buscarPorRol(rol);
        } else {
            temasFiltrados = temaService.listarTodos();
        }

        model.addAttribute("temas", temasFiltrados);
        model.addAttribute("materias", materiaRepository.findAll());
        model.addAttribute("tutores", usuarioRepository.findByTipoUsuario("tutor"));
        return "home";
    }


}
