package com.uca.parcialfinalncapas.service;

import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Opción 1: Buscar por correo (si usas el correo como username)
        User user = userRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + username));

        // Opción 2: Si prefieres buscar por username (debes añadir el campo a tu entidad)
        // User user = userRepository.findByUsername(username);
        // if (user == null) {
        //     throw new UsernameNotFoundException("Usuario no encontrado con username: " + username);
        // }

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getNombreRol())
        );

        return new org.springframework.security.core.userdetails.User(
                user.getCorreo(),  // o user.getUsername() si usas username
                user.getPassword(),
                authorities
        );
    }
}