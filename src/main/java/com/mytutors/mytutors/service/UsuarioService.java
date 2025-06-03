package com.mytutors.mytutors.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.UsuarioRepository;
import com.mytutors.mytutors.model.Carrera;
import com.mytutors.mytutors.repository.CarreraRepository;
import java.util.List;
import com.mytutors.mytutors.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    private final CarreraRepository carreraRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository repo, CarreraRepository carreraRepo)
    {
        this.repo = repo;
        this.carreraRepo = carreraRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
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


    public String obtenerNombrePorId(Long id) {
        return usuarioRepository.findById(id)
                .map(Usuario::getNombre)
                .orElse("Desconocido");
    }

}
