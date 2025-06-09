package com.mytutors.mytutors.repository;

import com.mytutors.mytutors.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mytutors.mytutors.model.Tema;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface TemaRepository extends JpaRepository<Tema, Long> {
    List<Tema> findByTutorId(Long idTutor);
    List<Tema> findByTutorIsNull(); // sin tutor
    List<Tema> findByMateriaId(Long idMateria);
    List<Tema> findByRol(String rol);
    List<Tema> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String nombre, String descripcion);

    List<Tema> findByTutorIsNullOrCreadorIsNull();
    List<Tema> findByTutorOrCreador(Usuario usuario, Usuario creador);
    List<Tema> findByIdTutorIsNullOrIdCreadorIsNull();

    @EntityGraph(attributePaths = {"creador", "tutor", "materia"})
    @Override
    List<Tema> findAll();

}
