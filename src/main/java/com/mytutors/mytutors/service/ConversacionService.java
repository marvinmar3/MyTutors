package com.mytutors.mytutors.service;

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

    public List<Conversacion> obtenerConversaciones(Long usuarioId) {
        return conversacionRepository.findByUsuarioId(usuarioId);
    }
}
