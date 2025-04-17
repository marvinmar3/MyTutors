package com.mytutors.mytutors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.repository.TemaRepository;

@Service
public class TemaService {

    @Autowired
    private TemaRepository temaRepository;

    //obtener todos los temas registrados
    public List<Tema> obtenerTemas()
    {
        return temaRepository.findAll();
    }

    // para filtrar por tutor
    public List<Tema> buscarPorTutor(Long idTutor){
        return temaRepository.findByTutorId(idTutor);
    }

    //filtrar por materia
    public List<Tema> buscarPorMateria(Long idMateria)
    {
        return temaRepository.findByMateriaId(idMateria);
    }
}
