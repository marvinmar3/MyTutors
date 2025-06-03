package com.mytutors.mytutors.repository;

import com.mytutors.mytutors.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    List<Usuario> findByRolEnApp(String rolEnApp);
    List<Usuario> findByTipoUsuario(String tipoUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.id IN (" +
            "SELECT p.idUsuario FROM ParticipanteConversacion p " +
            "WHERE p.idConversacion = :idConversacion AND p.idUsuario <> :idUsuario)")
    List<Usuario> obtenerOtroParticipante(@Param("idConversacion") Long idConversacion,
                                    @Param("idUsuario") Long idUsuario);


}
