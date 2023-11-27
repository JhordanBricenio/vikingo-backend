package com.codej.licoreria.security;

import com.codej.licoreria.model.Usuario;
import com.codej.licoreria.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe para el email: " + email));

        return User.withUsername(email)
                .password(usuario.getPassword())
                .roles(usuario.getRoles().stream().map(r -> r.getNombre()).toArray(String[]::new))
                .build();
    }

}
