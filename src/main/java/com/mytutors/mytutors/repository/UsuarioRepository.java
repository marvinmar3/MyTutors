package com.mytutors.mytutors.repository;

import com.mytutors.mytutors.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{
    Usuario findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    List<Usuario> findByRolEnApp(String rolEnApp);
}
