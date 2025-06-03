package com.mytutors.mytutors.service;

import com.mytutors.mytutors.model.Reporte;
import com.mytutors.mytutors.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.mytutors.mytutors.model.EstadoReporte;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    public void guardarReporte(Reporte reporte){
        reporteRepository.save(reporte);
    }

    public List<Reporte> obtenerPendientes(){
        return reporteRepository.findByEstado(EstadoReporte.PENDIENTE);
    }

    public Long contarPendientes(){
        return reporteRepository.contarPendientes();

    }

    public void cambiarEstado(Long id, EstadoReporte nuevoEstado) {
        Reporte reporte = reporteRepository.findById(id).orElse(null);
        if (reporte != null) {
            reporte.setEstado(nuevoEstado);
            reporteRepository.save(reporte);
        }
    }



}

