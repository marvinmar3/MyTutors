package com.mytutors.mytutors.repository;

import com.mytutors.mytutors.model.Conversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConversacionRepository extends JpaRepository <Conversacion, Long> {
    @Query("SELECT c FROM Conversacion c JOIN ParticipanteConversacion pc ON c.id = pc.idConversacion WHERE pc.idUsuario = ?1")
    List<Conversacion> findByUsuarioId(Long usuarioId);

}
