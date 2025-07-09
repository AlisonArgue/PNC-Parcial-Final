package com.uca.parcialfinalncapas.service;

import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.exceptions.UserNotFoundException;
import com.uca.parcialfinalncapas.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.*;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDService implements UserDetailsService {

    private UserRepository userRepository;

    public UserDService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDetails loadUserByEmail(String correo) throws UserNotFoundException {
        Optional<User> user = userRepository.findByCorreo(correo);
        if (user == null) {
            throw new UserNotFoundException("User Not Found with email: " + correo);
        }
        User found = user.get();
        return new org.springframework.security.core.userdetails.User(
                found.getCorreo(),
                found.getPassword(),
                Collections.emptyList()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
