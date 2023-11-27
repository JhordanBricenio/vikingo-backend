package com.codej.licoreria.service;


import com.codej.licoreria.model.Usuario;
import org.springframework.http.ResponseEntity;


public interface IUsuarioService {
    public Usuario findById(Integer id);
    public Usuario save (Usuario usuario);

    public Usuario findByEmail(String email);
}
