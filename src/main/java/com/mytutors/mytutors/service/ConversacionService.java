package com.mytutors.mytutors.service;

import com.mytutors.mytutors.dto.ConversacionVistaDTO;
import com.mytutors.mytutors.model.Mensaje;
import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.mytutors.mytutors.model.Conversacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


import java.util.List;

@Service
public class ConversacionService {
    @Autowired
    private ConversacionRepository conversacionRepository;

    @Autowired
    private ParticipanteConversacionRepository participantesRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private TemaRepository temaRepository;

    public List<ConversacionVistaDTO> obtenerConversaciones(Long usuarioId) {
        List<Conversacion> conversaciones = conversacionRepository.findByUsuarioId(usuarioId);
        Map<Long, ConversacionVistaDTO> mapa = new LinkedHashMap<>();

        for (Conversacion conv : conversaciones) {
            // Obtener al otro participante
            List<Usuario> otros = usuarioRepository.obtenerOtroParticipante(conv.getId(), usuarioId);
            Usuario otro = (!otros.isEmpty()) ? otros.get(0) : null;

            if (otro == null || mapa.containsKey(conv.getId())){
                System.out.println("Participante no encontrado para conversacion " + conv.getId());
                continue;
            }
            System.out.println("Otro participante: "+ otro.getNombre() + otro.getId());

            ConversacionVistaDTO dto = new ConversacionVistaDTO();
            dto.setId(conv.getId());

            // Establecer nombre y avatar
            String nombre = otro.getNombre();
            dto.setNombre((nombre == null || nombre.trim().isEmpty() || nombre.equalsIgnoreCase("false"))
                    ? "Usuario desconocido"
                    : nombre);

            String ruta = otro.getRutaFoto();
            dto.setAvatar((ruta == null || ruta.trim().isEmpty() || ruta.equalsIgnoreCase("false"))
                    ? "/img/default-user.jpeg"
                    : ruta);

            // Obtener último mensaje
            mensajeRepository.findTopByIdConversacionOrderByFechaEnvioDesc(conv.getId()).ifPresent(mensaje -> {
                dto.setUltimoMensaje(mensaje.getContenido());
                dto.setFechaUltimoMensaje(mensaje.getFechaEnvio());
            });

            mapa.put(conv.getId(), dto);
        }

        return new ArrayList<>(mapa.values());
    }


    private ConversacionVistaDTO crearDTO(Conversacion conv, Long usuarioId) {
        ConversacionVistaDTO dto = new ConversacionVistaDTO();
        dto.setId(conv.getId());
        dto.setNombre("Sin nombre");
        dto.setAvatar("/img/default-user.jpeg");

        if("grupo".equals(conv.getTipo())) {
            if (conv.getNombre() != null && !conv.getNombre().isBlank()) {
                dto.setNombre(conv.getNombre());
            }
            dto.setAvatar("/img/default-group.png");
        }else {
            List<Usuario> otros = usuarioRepository.obtenerOtroParticipante(conv.getId(), usuarioId);
            Usuario otro = (otros != null && !otros.isEmpty()) ? otros.get(0) : null;

            if(otro!= null){
                String nombre = otro.getNombre();
                if(nombre == null || nombre.trim().isEmpty() || nombre.equals("false")){
                    nombre = "Usuario desconocido";
                }
                dto.setNombre(nombre);
                String ruta= otro.getRutaFoto();
                if(ruta == null || ruta.trim().isEmpty() || ruta.equals("false")){
                    dto.setAvatar("/img/default-user.png");
                }else{
                    dto.setAvatar(ruta);
                }
            }else{
                dto.setNombre("Usuario eliminado");
                dto.setAvatar("/img/default-user.png");
            }
        }

        Mensaje ultimo = mensajeRepository.findTopByIdConversacionOrderByFechaEnvioDesc(conv.getId()).orElse(null);
        if(ultimo != null) {
            dto.setUltimoMensaje(ultimo.getContenido());
            dto.setFechaUltimoMensaje(ultimo.getFechaEnvio());
        }
        return dto;

    }

    @Transactional
    public Conversacion obtenerOCrearConversacionIndividual(Tema tema) {
        if (tema.getTutor() == null || tema.getCreador() == null) {
            return null;
        }

        // Buscar si ya existe una conversación para este tema
        Optional<Conversacion> existente = conversacionRepository.findByTemaId(tema.getId());
        if (existente.isPresent()) {
            return existente.get();
        }

        // Crear nueva conversación
        Conversacion nueva = new Conversacion();
        nueva.setTema(tema);
        nueva.setTipo("individual");
        nueva.setNombre("Chat entre " + tema.getCreador().getNombre() + " y " + tema.getTutor().getNombre());

        List<Usuario> participantes = new ArrayList<>();
        participantes.add(tema.getCreador());
        participantes.add(tema.getTutor());
        for (Usuario participante : participantes) {
            if (participante.getConversaciones() == null) {
                participante.setConversaciones(new ArrayList<>());
            }
            participante.getConversaciones().add(nueva);
        }
        nueva.setParticipantes(participantes);


        return conversacionRepository.save(nueva);
    }


}
