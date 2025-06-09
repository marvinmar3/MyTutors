package com.mytutors.mytutors.service;

import com.mytutors.mytutors.dto.TemaVistaDTO;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mytutors.mytutors.model.Materia;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.repository.TemaRepository;

@Service
public class TemaService {

    @Autowired
    private TemaRepository temaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    public Optional<Tema> buscarPorId(Long id){
        return temaRepository.findById(id);
    }

    //obtener todos los temas registrados
    public List<Tema> obtenerTemas()
    {
        return temaRepository.findAll();
    }

    // para filtrar por tutor
//    public List<Tema> buscarPorTutor(Long idTutor){
//        return temaRepository.findById(idTutor);
//    }

    //filtrar por materia
    public List<Tema> buscarPorMateria(Long idMateria)
    {
        return temaRepository.findByMateriaId(idMateria);
    }
    public List<Tema> buscarSinTutor() {
        return temaRepository.findByTutorIsNull();
    }
    public List<Tema> buscarPorTutor(Long idTutor) {
        return temaRepository.findByTutorId(idTutor);
    }

    public List<Tema> buscarPorRol(String rol) {
        return temaRepository.findByRol(rol);
    }

    public List<Tema> buscarPorTexto(String texto) {
        return temaRepository.findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(texto, texto);
    }

    public List<Tema> listarTodos() {
        return temaRepository.findAll();
    }

    public void guardarTema(Tema tema, Usuario usuario, String rol) {
        Materia materia = materiaRepository.findById(tema.getIdMateria()).orElseThrow();
        tema.setMateria(materia);

        if ("tutor".equalsIgnoreCase(rol)) {
            tema.setIdTutor(usuario.getId());
            tema.setTutor(usuario);
            tema.setIdCreador(null);
            tema.setCreador(null);
        } else if ("tutorado".equalsIgnoreCase(rol)) {
            tema.setIdCreador(usuario.getId());
            tema.setCreador(usuario);
            tema.setIdTutor(null);
            tema.setTutor(null);
        }

        temaRepository.save(tema);
    }


    public List<TemaVistaDTO> obtenerTemasVistas(List<Tema> temas) {
        return temas.stream().map(TemaVistaDTO::new).toList();
    }

}
