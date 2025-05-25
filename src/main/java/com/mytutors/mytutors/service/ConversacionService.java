package com.mytutors.mytutors.service;

import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.mytutors.mytutors.model.Conversacion;
import com.mytutors.mytutors.repository.ConversacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversacionService {
    @Autowired
    private ConversacionRepository conversacionRepository;

    @Autowired
    private TemaRepository temaRepository;

    public List<Conversacion> obtenerConversaciones(Long usuarioId) {
        return conversacionRepository.findByUsuarioId(usuarioId);
    }

    public Conversacion obtenerOCrearPorTema(Long idTema) {
        return conversacionRepository.findByTemaId(idTema).orElseGet(()->{
            Tema tema = temaRepository.findById(idTema).orElse(null);
            if(tema==null) return null;

            Conversacion nueva = new Conversacion();
            nueva.setTema(tema);
            nueva.setNombre("Char del tema: "+tema.getNombre());

            return conversacionRepository.save(nueva);
        });
    }

}
