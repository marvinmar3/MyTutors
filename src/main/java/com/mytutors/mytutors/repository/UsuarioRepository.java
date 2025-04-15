package com.mytutors.mytutors.repository;

import com.mytutors.mytutors.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{
    Usuario findByCorreo(String correo);
}
