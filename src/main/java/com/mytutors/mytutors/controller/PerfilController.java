package com.mytutors.mytutors.controller;

import com.mytutors.mytutors.model.Usuario;
import com.mytutors.mytutors.repository.CarreraRepository;
import com.mytutors.mytutors.repository.FacultadRepository;
import com.mytutors.mytutors.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.UUID;
import java.io.IOException;


@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private FacultadRepository facultadRepository;
    @Autowired
    private CarreraRepository carreraRepository;


    @GetMapping
    public String mostrarPerfil(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("facultades", facultadRepository.findAll());
        model.addAttribute("carreras", carreraRepository.findByFacultad(usuario.getFacultad()));
        return "perfil";
    }

    private String guardarImagen(MultipartFile archivo) {
        try {
            if (!archivo.isEmpty()) {
               //validar el nombre del archivo
                String nombreOriginal = archivo.getOriginalFilename();
                if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
                    System.out.println("⚠️ El archivo no tiene nombre válido.");
                    return null;
                }

                String nombreArchivo = UUID.randomUUID().toString() + "_" + nombreOriginal;


                String ruta = "src/main/resources/static/img/usuarios/";
                Path directorio = Paths.get(ruta);
                if (!Files.exists(directorio)) {
                    Files.createDirectories(directorio);
                    System.out.println("🗂️ Directorio creado en: " + directorio.toAbsolutePath());
                }

                Path rutaArchivo = Paths.get(ruta + nombreArchivo);
                Files.write(rutaArchivo, archivo.getBytes());

                System.out.println("✅ Imagen guardada en: " + rutaArchivo.toAbsolutePath());

                return nombreArchivo;
            } else {
                System.out.println("⚠️ El archivo está vacío");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Error al intentar guardar la imagen.");
        }
        return null;
    }


    @PostMapping("/actualizar")
    public String actualizarPerfil(@RequestParam("nombre") String nombre,
                                   @RequestParam("facultadId") Long facultadId,
                                   @RequestParam("carreraId") Long carreraId,
                                   @RequestParam("imagen") MultipartFile imagen,
                                   HttpSession session, Model model){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        usuario.setNombre(nombre);
        usuario.setFacultad(facultadRepository.findById(facultadId).orElse(null));
        usuario.setCarrera(carreraRepository.findById(carreraId).orElse(null));

        //guarda la imagen si se sube alguna
        if(!imagen.isEmpty())
        {
            if(usuario.getRutaFoto()!=null)
            {
                Path rutaAnterior = Paths.get("src/main/resources/static/img/usuarios/" + usuario.getRutaFoto());
                try{
                    Files.deleteIfExists(rutaAnterior);
                    System.out.println("Imagen anterior eliminada correctamente: "+rutaAnterior.toAbsolutePath());
                }catch(IOException e)
                {
                    System.out.println("No se pudo eliminar la imagen anterior: ");
                    e.printStackTrace();
                }
            }
            String rutaImagen = guardarImagen(imagen);
            if(rutaImagen != null)
            {
                usuario.setRutaFoto(rutaImagen);
                System.out.println("Imagen guardada en: "+rutaImagen);
                session.setAttribute("usuario", usuario);
            }else {
                System.out.println("Error al guardar imagen");
            }

        }
        // No actualizar el correo ni la contraseña aquí
        usuarioRepository.save(usuario);
        //session.setAttribute("usuario", usuario);
        return "redirect:/perfil";
    }
}
