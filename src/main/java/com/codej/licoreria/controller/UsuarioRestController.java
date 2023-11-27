package com.codej.licoreria.controller;

import com.codej.licoreria.model.Rol;
import com.codej.licoreria.model.Usuario;
import com.codej.licoreria.service.IUploadService;
import com.codej.licoreria.service.IUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
@AllArgsConstructor
public class UsuarioRestController {

    private final IUsuarioService usuarioService;
    private final IUploadService uploadService;



    @PostMapping()
    public Usuario save(@RequestBody Usuario usuario){
       return usuarioService.save(usuario);
    }

    @GetMapping("/{id}")
    public Usuario show(@PathVariable Integer id){
        return usuarioService.findById(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile archivo,
                                    @RequestParam("id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        Usuario usuario = usuarioService.findById(id);
        if(!archivo.isEmpty()){
            String nombreArchivo= null;
            try {
                nombreArchivo= uploadService.copiar(archivo);
            }catch (IOException e){
                response.put("mensaje", "Error al subir la imagen del usuario");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String nombreFotoAnt= usuario.getFoto();
            uploadService.eliminar(nombreFotoAnt);
            usuario.setFoto(nombreArchivo);
            usuarioService.save(usuario);
            response.put("usuario", usuario);
            response.put("mensaje", "Has subido corectamente la imagen"+nombreArchivo);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    //Buscar usuario por email
    @GetMapping("/findByEmail/{email}")
    public Usuario findByEmail(@PathVariable String email){
        return usuarioService.findByEmail(email);
    }



}
