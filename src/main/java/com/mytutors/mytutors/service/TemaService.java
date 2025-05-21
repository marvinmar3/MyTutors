package com.mytutors.mytutors.service;

import com.mytutors.mytutors.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.repository.TemaRepository;

@Service
public class TemaService {

    @Autowired
    private TemaRepository temaRepository;

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
        if("tutor".equalsIgnoreCase(rol)){
            tema.setTutor(usuario);
        }else{
            tema.setTutor(null);
        }
        tema.setIdCreador(usuario.getId());
        tema.setCreador(usuario);
        tema.setRol(rol);

        temaRepository.save(tema);
    }

}
