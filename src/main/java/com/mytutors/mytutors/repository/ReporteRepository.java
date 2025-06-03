package com.mytutors.mytutors.repository;

import com.mytutors.mytutors.model.Reporte;
import com.mytutors.mytutors.model.EstadoReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    @Query("SELECT COUNT(r) FROM Reporte r WHERE r.estado = com.mytutors.mytutors.model.EstadoReporte.PENDIENTE")
    Long contarPendientes();

    List<Reporte> findByEstado(EstadoReporte estado);

    Long countByEstado(EstadoReporte estado);
}
