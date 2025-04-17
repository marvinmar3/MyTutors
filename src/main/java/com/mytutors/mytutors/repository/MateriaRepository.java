package com.mytutors.mytutors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mytutors.mytutors.model.Materia;
import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, Long> {

}
