package com.mytutors.mytutors.repository;

import com.mytutors.mytutors.model.ParticipanteConversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipanteConversacionRepository extends JpaRepository<ParticipanteConversacion, Long> {
    List<ParticipanteConversacion> findByIdUsuario(Long idUsuario);
    List<ParticipanteConversacion> findByIdConversacion(Long idConversacion);
}
