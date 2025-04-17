package com.mytutors.mytutors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mytutors.mytutors.model.Tema;
import java.util.List;


public interface TemaRepository extends JpaRepository<Tema, Long> {
    List<Tema> findByTutorId(Long idTutor);

    List<Tema> findByMateriaId(Long idMateria);
}
