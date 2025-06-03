package com.mytutors.mytutors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mytutors.mytutors.model.Mensaje;
import java.util.List;
import java.util.Optional;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    List<Mensaje> findByIdConversacionOrderByFechaEnvioAsc(Long idConversacion);

    Optional<Mensaje> findTopByIdConversacionOrderByFechaEnvioDesc(Long idConversacion);

    List<Mensaje> findByIdConversacionOrderByFechaEnvioDesc(Long idConversacion);


}
