package com.mytutors.mytutors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mytutors.mytutors.model.Materia;
import java.util.List;
import com.mytutors.mytutors.model.Carrera;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    List<Materia> findByCarrera(Carrera carrera);
    List<Materia> findByNombreIgnoreCaseAndCarrera_Id(String nombre, Long idCarrera);
}
