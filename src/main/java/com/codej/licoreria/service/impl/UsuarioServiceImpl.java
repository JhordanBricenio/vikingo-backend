package com.codej.licoreria.service.impl;


import com.codej.licoreria.model.Rol;
import com.codej.licoreria.model.Usuario;
import com.codej.licoreria.repository.IUsuarioRepository;
import com.codej.licoreria.service.IUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private  PasswordEncoder passwordEncoder;

    @Override
    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Rol rol = new Rol();
        rol.setId(2);
        usuario.agregarRol(rol);//Asiganmos Rol=Vendedor
        return usuarioRepository.save(usuario);
    }


    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
}
