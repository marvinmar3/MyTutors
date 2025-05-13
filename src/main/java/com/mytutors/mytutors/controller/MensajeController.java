package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.model.Mensaje;
import com.mytutors.mytutors.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;


public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @GetMapping("/mensajes/{idConversacion}")
    @ResponseBody
    public List<Mensaje> obtenerMensajes(@PathVariable Long idConversacion){
        return mensajeService.obtenerMensajes(idConversacion);
    }
}
