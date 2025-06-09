package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.dto.TemaVistaDTO;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.MateriaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;


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
                            @RequestParam(name = "id_materia", required = false) String idMateriaStr,
                            @RequestParam(name = "nuevaMateria", required = false) String nuevaMateria,
                            @RequestParam(name = "id_facultad") Long idFacultad,
                            @RequestParam(name = "id_carrera") Long idCarrera,
                            @RequestParam String rol,
                            HttpSession session,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        Materia materia =null;
        Long idMateria = null;

        boolean esMateriaNueva = "otra".equalsIgnoreCase(idMateriaStr);

        if (esMateriaNueva && nuevaMateria != null && !nuevaMateria.isBlank()) {
            // Validar que no exista una materia con ese nombre en esa carrera
            List<Materia> existentes = materiaRepository.findByNombreIgnoreCaseAndCarrera_Id(nuevaMateria.trim(), idCarrera);
            if (!existentes.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "⚠️ Ya existe una materia con ese nombre en la carrera seleccionada.");
                return "redirect:/temas/nuevo";
            }

            Carrera carrera = carreraRepository.findById(idCarrera)
                    .orElseThrow(() -> new IllegalArgumentException("Carrera no encontrada para id: " + idCarrera));

            materia = new Materia();
            materia.setNombre(nuevaMateria.trim());
            materia.setCarrera(carrera);
            materia.setFacultad(carrera.getFacultad());
            materiaRepository.save(materia);
        } else {
            try {
                idMateria = Long.parseLong(idMateriaStr);
            } catch (NumberFormatException e) {
                return "redirect:/temas/nuevo"; // o mostrar mensaje de error
            }

            materia = materiaRepository.findById(idMateria).orElse(null);
        }

        if (materia != null) {
            Tema tema = new Tema();
            tema.setNombre(nombre.trim());
            tema.setDescripcion(descripcion.trim());
            tema.setIdMateria(materia.getId());
            tema.setMateria(materia);
            tema.setIdCreador(usuario.getId());
            tema.setRol(rol);

            temaService.guardarTema(tema, usuario, rol);
            redirectAttributes.addFlashAttribute("exito", "La tutoría fue creada exitosamente!");
            return "redirect:/home";
        }else{
            redirectAttributes.addFlashAttribute("error", "No se pudo registrar el tema por datos incompletos.");
            return "redirect:/temas/nuevo";
        }
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
        if (idFacultad == null) return Collections.emptyList();
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

    @GetMapping("/ver")
    public String verTema(@RequestParam ("idTema") Long idTema, Model model, HttpSession session) {

        if(idTema == null) {
            return "redirect:/home";
        }
        Tema tema = temaRepository.findById(idTema).orElse(null);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if(tema!=null) {
            model.addAttribute("tema", tema);
            model.addAttribute("usuario", usuario);

            boolean match =(tema.getTutor()!=null && tema.getTutor().getId().equals(usuario.getId())) ||
                    (tema.getCreador()!= null && tema.getCreador().getId().equals(usuario.getId()));

            model.addAttribute("match", match);
            return "verTema";
        }
        return "redirect:/home";
    }

    @GetMapping("/nuevo")
    public String mostrarFormNuevoTema(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario==null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("facultades", facultadRepository.findAll());
        model.addAttribute("materias", materiaRepository.findAll());

        return "nuevoTema";

    }

    @GetMapping("/aprendizaje")
    public String verAprendizajes(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario==null) {
            return "redirect:/login";
        }
        List<Tema> temasUsuario=temaRepository.findByTutorOrCreador(usuario,usuario);
        List<TemaVistaDTO> aprendizajes= temaService.obtenerTemasVistas(temasUsuario);

        model.addAttribute("aprendizajes", aprendizajes);
        return "aprendizaje";
    }
}
