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

    public List<SolicitudNotificacionDTO> obtenerSolicitudesDTO(Long idCreador) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if(idCreador == null){
            return Collections.emptyList();
        }
        List<SolicitudTutoria> solicitudes = solicitudRepo.findByTema_Creador_IdAndRespondidaFalse(idCreador);

        return solicitudes.stream().map(s -> {
            SolicitudNotificacionDTO dto = new SolicitudNotificacionDTO();
            dto.setId(s.getId());
            dto.setFechaSolicitud(s.getFechaSolicitud());
            dto.setNombreTema(s.getTema() != null ? s.getTema().getNombre() : "Sin título");
            dto.setNombreSolicitante(s.getSolicitante() != null ? s.getSolicitante().getNombre() : "Desconocido");
            return dto;
        }).toList();
    }


    public void aceptarSolicitud(Long idSolicitud){
        SolicitudTutoria solicitud = solicitudRepo.findById(idSolicitud).orElseThrow();
        Tema tema = solicitud.getTema();

        tema.setTutor(solicitud.getSolicitante());
        temaRepo.save(tema);

        solicitud.setAceptada(true);
        solicitud.setRespondida(true);
        solicitudRepo.save(solicitud);

    }

    public void rechazarSolicitud(Long idSolicitud){
        SolicitudTutoria solicitud = solicitudRepo.findById(idSolicitud).orElseThrow();
        solicitud.setAceptada(false);
        solicitud.setRespondida(false);
        solicitudRepo.save(solicitud);
    }

}
