package com.mytutors.mytutors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mytutors.mytutors.model.Carrera;
import com.mytutors.mytutors.model.Facultad;
import java.util.List;


public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    List<Carrera> findByFacultad(Facultad facultad);
}
