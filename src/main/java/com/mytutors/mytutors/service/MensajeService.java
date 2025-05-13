package com.mytutors.mytutors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mytutors.mytutors.model.Mensaje;
import com.mytutors.mytutors.repository.MensajeRepository;
import java.util.List;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    public void guardarMensaje(Mensaje mensaje) {
        mensaje.setLeido(false); //por defecto
        mensajeRepository.save(mensaje);
    }

    public List<Mensaje> obtenerMensajes(Long idConversacion){
        return mensajeRepository.findByIdConversacion(idConversacion);
    }
}
