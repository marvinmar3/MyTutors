package com.mytutors.mytutors.service;

import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mytutors.mytutors.model.Mensaje;
import com.mytutors.mytutors.repository.MensajeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.mytutors.mytutors.service.UsuarioService;

import com.mytutors.mytutors.dto.ChatMessageDTO;


@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void guardarMensaje(Mensaje mensaje) {
        mensaje.setLeido(false); //por defecto
        mensajeRepository.save(mensaje);
    }

    public List<Mensaje> obtenerMensajesDeConversacion(Long idConversacion) {
        return mensajeRepository.findByIdConversacionOrderByFechaEnvioAsc(idConversacion);
    }

    public Mensaje guardar(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }
    public void guardarDesdeDTO(ChatMessageDTO dto) {
        Mensaje mensaje = new Mensaje();
        mensaje.setIdConversacion(dto.getIdConversacion());
        mensaje.setIdEmisor(dto.getIdEmisor());
        mensaje.setContenido(dto.getContenido());
        mensaje.setFechaEnvio(LocalDateTime.now());
        mensaje.setLeido(false);
        mensajeRepository.save(mensaje);
    }

//    public List<ChatMessageDTO> obtenerMensajesConNombre(Long idConversacion) {
//        return mensajeRepository.findByIdConversacionOrderByFechaEnvioDesc(idConversacion)
//                .stream()
//                .map(m -> {
//                    System.out.println("Mensaje cargado: "+m.getContenido());
//                    ChatMessageDTO dto = new ChatMessageDTO();
//                    dto.setIdConversacion(m.getIdConversacion());
//                    dto.setIdEmisor(m.getIdEmisor());
//                    dto.setContenido(m.getContenido());
//                    dto.setFechaEnvio(m.getFechaEnvio());
//                    dto.setNombreEmisor(usuarioService.obtenerNombrePorId(m.getIdEmisor()));
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }


    public List<ChatMessageDTO> obtenerMensajesConNombre(Long idConversacion) {
        List<Mensaje> mensajes = mensajeRepository.findByIdConversacionOrderByFechaEnvioAsc(idConversacion);
        return mensajes.stream().map(m -> {
            ChatMessageDTO dto = new ChatMessageDTO();
            dto.setIdConversacion(m.getIdConversacion());
            dto.setIdEmisor(m.getIdEmisor());
            dto.setContenido(m.getContenido());
            dto.setFechaEnvio(m.getFechaEnvio());

            if (m.getIdEmisor() != null) {
                Optional<Usuario> usuario = usuarioRepository.findById(m.getIdEmisor());
                dto.setNombreEmisor(usuario.map(Usuario::getNombre).orElse("Desconocido"));
            } else {
                dto.setNombreEmisor("Desconocido");
            }

            return dto;
        }).toList();
    }


}
