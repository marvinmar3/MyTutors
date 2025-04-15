package com.mytutors.mytutors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.UsuarioRepository;
import com.mytutors.mytutors.model.Carrera;
import com.mytutors.mytutors.repository.CarreraRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    private final CarreraRepository carreraRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UsuarioService(UsuarioRepository repo, CarreraRepository carreraRepo)
    {
        this.repo = repo;
        this.carreraRepo = carreraRepo;
    }

    public void registrar(Usuario u, Long idCarrera)
    {
        u.setPassword(encoder.encode(u.getPassword()));
        Carrera carrera = carreraRepo.findById(idCarrera).orElse(null);
        u.setCarrera(carrera);
        if (carrera != null) {
            u.setFacultad(carrera.getFacultad());
        }

        repo.save(u);
    }

    public Usuario buscarPorCorreo(String correo)
    {
        return repo.findByCorreo(correo);
    }

}
