package com.mytutors.mytutors.service;

import com.mytutors.mytutors.model.Usuario;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;

//@Service
/*public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario= usuarioService.buscarPorCorreo(correo);
        if(usuario==null)
        {
            throw new UsernameNotFoundException("Usuario no encontrado con correo: "+correo);
        }

        return new User(usuario.getCorreo(), usuario.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}*/
