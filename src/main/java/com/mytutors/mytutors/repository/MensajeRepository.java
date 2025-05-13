package com.mytutors.mytutors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mytutors.mytutors.model.Mensaje;
import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByIdConversacion(Long idConversacion);
}
