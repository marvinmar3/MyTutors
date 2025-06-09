package com.mytutors.mytutors.service;

import com.mytutors.mytutors.dto.SolicitudNotificacionDTO;
import com.mytutors.mytutors.model.SolicitudTutoria;
import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.SolicitudTutoriaRepository;
import com.mytutors.mytutors.repository.TemaRepository;
import com.mytutors.mytutors.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolicitudTutoriaService {

    @Autowired
    private SolicitudTutoriaRepository solicitudRepo;

    @Autowired
    private TemaRepository temaRepo;
    @Autowired
    private UsuarioRepository usuarioRepository;


    public void crearSolicitud(Long idUsuario, Long idTema){
        Optional<SolicitudTutoria> existente = solicitudRepo.findBySolicitante_IdAndTema_Id(idUsuario, idTema);
        if(existente.isPresent()){
            return; // ya hay solicitu hecha
        }
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow();
        Tema tema = temaRepo.findById(idTema).orElseThrow();

        SolicitudTutoria solicitud = new SolicitudTutoria();
        solicitud.setSolicitante(usuario);
        solicitud.setTema(tema);
        solicitud.setFechaSolicitud(LocalDateTime.now());
        solicitudRepo.save(solicitud);
        System.out.println("✔️ Creando solicitud de usuario " + idUsuario + " para tema " + idTema);

    }

    public List<SolicitudNotificacionDTO> obtenerSolicitudesDTO(Long idUsuario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if(idUsuario == null){
            return Collections.emptyList();
        }
        List<SolicitudTutoria> solicitudes = solicitudRepo.findByTema_Creador_IdOrTema_Tutor_IdAndRespondidaFalse(idUsuario, idUsuario);

        return solicitudes.stream().map(s -> {
            SolicitudNotificacionDTO dto = new SolicitudNotificacionDTO();
            dto.setId(s.getId());
            dto.setFechaSolicitud(s.getFechaSolicitud());
            dto.setNombreTema(s.getTema() != null ? s.getTema().getNombre() : "Sin título");
            dto.setNombreSolicitante(s.getSolicitante() != null ? s.getSolicitante().getNombre() : "Desconocido");
            return dto;
        }).toList();
    }


    public void aceptarSolicitud(Long idSolicitud) {
        SolicitudTutoria solicitud = solicitudRepo.findById(idSolicitud).orElseThrow();
        solicitud.setAceptada(true);
        solicitud.setRespondida(true);

        Tema tema = solicitud.getTema();
        Usuario solicitante = solicitud.getSolicitante();

        if ("tutor".equalsIgnoreCase(tema.getRol())) {
            // El tutor ya creó el tema, asignar tutorado
            if (tema.getIdCreador() == null) {
                tema.setIdCreador(solicitante.getId());
                tema.setCreador(solicitante);
            }
        } else if ("tutorado".equalsIgnoreCase(tema.getRol())) {
            // El tutorado ya creó el tema, asignar tutor
            if (tema.getIdTutor() == null) {
                tema.setIdTutor(solicitante.getId());
                tema.setTutor(solicitante);
            }
        }

        temaRepo.save(tema);
        solicitudRepo.save(solicitud);
    }



    public void rechazarSolicitud(Long idSolicitud){
        System.out.println("➡️ Intentando rechazar solicitud con id: " + idSolicitud);
        try {
            SolicitudTutoria solicitud = solicitudRepo.findById(idSolicitud)
                    .orElseThrow(() -> new RuntimeException("❌ Solicitud no encontrada con id " + idSolicitud));

            solicitud.setAceptada(false);
            solicitud.setRespondida(true);
            solicitudRepo.save(solicitud);
            System.out.println("✔️ Solicitud rechazada exitosamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al rechazar solicitud: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


}
