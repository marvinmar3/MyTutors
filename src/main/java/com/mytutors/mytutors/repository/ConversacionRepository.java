package com.mytutors.mytutors.repository;

import com.mytutors.mytutors.model.Conversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversacionRepository extends JpaRepository <Conversacion, Long> {
    @Query("SELECT c FROM Conversacion c JOIN c.participantes p WHERE p.id = :idUsuario")
    List<Conversacion> findByUsuarioId(@Param("idUsuario") Long usuarioId);

    Optional<Conversacion>findByTemaId(Long idTema);

}
