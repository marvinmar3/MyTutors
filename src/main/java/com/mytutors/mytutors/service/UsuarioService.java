package com.mytutors.mytutors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.UsuarioRepository;
import com.mytutors.mytutors.model.Carrera;
import com.mytutors.mytutors.repository.CarreraRepository;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    private final CarreraRepository carreraRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository repo, CarreraRepository carreraRepo, PasswordEncoder passwordEncoder)
    {
        this.repo = repo;
        this.carreraRepo = carreraRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void registrar(Usuario u, Long idCarrera)
    {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        Carrera carrera = carreraRepo.findById(idCarrera).orElse(null);
        u.setCarrera(carrera);
        if (carrera != null) {
            u.setFacultad(carrera.getFacultad());
        }

        repo.save(u);
    }

    public Usuario buscarPorCorreo(String correo)
    {
        return repo.findByCorreo(correo).orElse(null);
    }

    public boolean existeCorreo(String correo)
    {
        return repo.existsByCorreo(correo);
    }

    public List<Usuario> buscarPorRolEnApp(String rolEnApp)
    {
        return repo.findByRolEnApp(rolEnApp);
    }

}
