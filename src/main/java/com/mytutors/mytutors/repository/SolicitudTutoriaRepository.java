package com.mytutors.mytutors.repository;

import com.mytutors.mytutors.model.SolicitudTutoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SolicitudTutoriaRepository extends JpaRepository<SolicitudTutoria, Long> {
    // solicitudes que yo realicé
    List<SolicitudTutoria> findBySolicitante_Id(Long idUsuario);

    //solicitudes o tutorias que yo creé y aun no he respondido
    List<SolicitudTutoria> findByTema_Creador_IdAndRespondidaFalse(Long idCreador);

    List<SolicitudTutoria> findByTema_Creador_IdOrTema_Tutor_IdAndRespondidaFalse(Long idCreador, Long idTutor);


    //verificar si ya solicité una tutoria
    Optional<SolicitudTutoria> findBySolicitante_IdAndTema_Id(Long idUsuario, Long idTema);

    @Query("""
    SELECT s FROM SolicitudTutoria s 
    WHERE 
        (s.tema.creador.id = :idUsuario OR s.tema.tutor.id = :idUsuario)
        AND s.respondida = false
""")
    List<SolicitudTutoria> findPendientesParaUsuarioResponsable(@Param("idUsuario") Long idUsuario);


}
