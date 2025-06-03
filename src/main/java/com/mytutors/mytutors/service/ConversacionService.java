package com.mytutors.mytutors.service;

import com.mytutors.mytutors.dto.ConversacionVistaDTO;
import com.mytutors.mytutors.model.Mensaje;
import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.mytutors.mytutors.model.Conversacion;
import org.springframework.stereotype.Service;

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
        Map<Long, ConversacionVistaDTO> mapa = new HashMap<>();

        for(Conversacion conv: conversaciones) {
            List<Usuario> otros = usuarioRepository.obtenerOtroParticipante(conv.getId(),usuarioId);
            Usuario otro = (otros != null && !otros.isEmpty()) ? otros.get(0) : null;

            if(otro ==null || mapa.containsKey(otro.getId())) continue;

            ConversacionVistaDTO dto = new ConversacionVistaDTO();
            dto.setId(conv.getId());

            //nombre
            String nombre= otro.getNombre();
            dto.setNombre((nombre == null || nombre.trim().isEmpty() || nombre.equalsIgnoreCase("false")) ? "Usuario desconocido" : nombre);

            //foto
            String ruta = otro.getRutaFoto();
            dto.setAvatar((ruta==null || ruta.trim().isEmpty() || ruta.equalsIgnoreCase("false"))? "/img/default-user.jpeg": ruta);

            mensajeRepository.findTopByIdConversacionOrderByFechaEnvioDesc(conv.getId())
                    .ifPresent(mensaje -> {
                        dto.setUltimoMensaje(mensaje.getContenido());
                        dto.setFechaUltimoMensaje(mensaje.getFechaEnvio());
                    });

            mapa.put(otro.getId(), dto);
        }


       /* for (Conversacion conv : conversaciones) {
            ConversacionVistaDTO dto = new ConversacionVistaDTO();
            dto.setId(conv.getId());

            // Por defecto
            dto.setNombre("Sin nombre");
            dto.setAvatar("/img/default-user.jpeg");

            if ("grupo".equals(conv.getTipo())) {
                if (conv.getNombre() != null && !conv.getNombre().isBlank()) {
                    dto.setNombre(conv.getNombre());
                }
                dto.setAvatar("/img/default-group.png"); // asegúrate de tener esta imagen
            } else {
                List<Usuario> otros= usuarioRepository.obtenerOtroParticipante(conv.getId(), usuarioId);
                System.out.println("Conversacion ID: " + conv.getId()+ ", Usuario actual:"+ usuarioId);
                System.out.println("Otros participantes encontrados:"+ (otros!= null ? otros.size(): "null"));

                Usuario otro = (otros!=null && !otros.isEmpty()) ? otros.get(0) : null;
                if (otro != null) {
                    System.out.println("- Usuario obtenido: "+ otro);
                    System.out.println("- Nombre: "+otro.getNombre());
                    System.out.println("- Ruta foto: "+otro.getRutaFoto());


                    String nombre = otro.getNombre();
                    if (nombre == null || nombre.trim().isEmpty() || nombre.equals("false")) {
                        nombre = "Usuario desconocido";
                    }
                    dto.setNombre(nombre);

                    String ruta = otro.getRutaFoto();
                    if (ruta == null || ruta.trim().isEmpty() || ruta.equals("false")) {
                        dto.setAvatar("/img/default-user.jpeg");
                    } else {
                        dto.setAvatar(ruta);
                    }

                } else {
                    dto.setNombre("Usuario eliminado");
                    dto.setAvatar("/img/default-user.jpeg");
                }
            }


            //ultimo msj
            Mensaje ultimo = mensajeRepository
                    .findTopByIdConversacionOrderByFechaEnvioDesc(conv.getId())
                    .orElse(null);

            if(ultimo != null){
                dto.setUltimoMensaje(ultimo.getContenido());
                dto.setFechaUltimoMensaje(ultimo.getFechaEnvio());
            }
            resultado.add(dto);
        }*/

        return new ArrayList<>(mapa.values());
    }

    public Conversacion obtenerOCrearPorTema(Long idTema) {
        return conversacionRepository.findByTemaId(idTema).orElseGet(()->{
            Tema tema = temaRepository.findById(idTema).orElse(null);
            if(tema==null) return null;

            Conversacion nueva = new Conversacion();
            nueva.setTema(tema);
            nueva.setNombre("Char del tema: "+tema.getNombre());
            nueva.setTipo("grupo");

            return conversacionRepository.save(nueva);
        });
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

}
