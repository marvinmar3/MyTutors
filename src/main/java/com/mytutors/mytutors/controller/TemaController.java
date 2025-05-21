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
import com.mytutors.mytutors.repository.CarreraRepository;
import com.mytutors.mytutors.model.Carrera;
import com.mytutors.mytutors.dto.CarreraDTO;
import com.mytutors.mytutors.model.Facultad;
import com.mytutors.mytutors.repository.FacultadRepository;

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

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private FacultadRepository facultadRepository;


    @PostMapping("/crear")
    public String crearTema(@RequestParam String nombre,
                            @RequestParam String descripcion,
                            @RequestParam (required = false) Long idMateria,
                            @RequestParam (required = false) String nuevaMateria,
                            @RequestParam Long idFacultad,
                            @RequestParam Long idCarrera,
                            @RequestParam String rol,
                            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        //Materia materia = materiaRepository.findById(idMateria).orElse(null);

        Materia materia;
        if ("otra".equals(String.valueOf(idMateria)) && nuevaMateria != null && !nuevaMateria.isBlank()) {
            materia = new Materia();
            materia.setNombre(nuevaMateria);
            materia.setCarrera(carreraRepository.findById(idCarrera).orElse(null));
            materiaRepository.save(materia); // guardar nueva materia
        } else {
            materia = materiaRepository.findById(idMateria).orElse(null);
        }

        if (usuario != null && materia != null) {
            Tema tema = new Tema();
            tema.setNombre(nombre);
            tema.setDescripcion(descripcion);
            tema.setIdMateria(idMateria);
            tema.setMateria(materia);
            tema.setIdCreador(usuario.getId());
            tema.setRol(rol);
            //tema.setTutor(usuario); // o tutorado, depende de cómo manejes esto

            temaService.guardarTema(tema, usuario, rol);

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
    @GetMapping("/carreras")
    @ResponseBody
    public List<CarreraDTO> obtenerCarrerasPorFacultad(@RequestParam("id_facultad") Long idFacultad) {
        Facultad facultad = facultadRepository.findById(idFacultad).orElse(null);
        List<Carrera> carreras = carreraRepository.findByFacultad(facultad);

        return carreras.stream()
                .map(c -> new CarreraDTO(c.getId(), c.getNombre()))
                .toList();
    }


    @GetMapping("/materias")
    @ResponseBody
    public List<Materia> obtenerMateriasPorCarrera(@RequestParam("id_carrera") Long idCarrera) {
        Carrera carrera = carreraRepository.findById(idCarrera).orElse(null);
        return materiaRepository.findByCarrera(carrera);
    }
}
