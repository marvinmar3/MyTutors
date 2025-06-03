package com.mytutors.mytutors.controller;


import com.mytutors.mytutors.model.Reporte;
import com.mytutors.mytutors.model.Tema;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.TemaRepository;
import com.mytutors.mytutors.service.ReporteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;
    @Autowired
    private TemaRepository temaRepository;

    @PostMapping("/crear")
    public String crearReporte(@RequestParam Long idTema,
                               @RequestParam String motivo,
                               @RequestParam String descripcion,
                               HttpServletRequest session) {
        Usuario emisor = (Usuario) session.getAttribute("usuario");

        Tema tema = temaRepository.findById(idTema).orElse(null);

        if(emisor != null && tema != null) {
            Reporte r = new Reporte();
            r.setMotivo(motivo);
            r.setDescripcion(descripcion);
            r.setFechaCreado(LocalDateTime.now());
            r.setEmisor(emisor);
            r.setTemaReportado(tema);
            reporteService.guardarReporte(r);
        }
        return "redirect:/home";
    }
}
