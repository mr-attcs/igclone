package com.mrattcs.instagramapi.service;

import com.mrattcs.instagramapi.repo.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.mrattcs.instagramapi.modal.User> oUserRepository = userRepository.findByEmail(username);

        if (oUserRepository.isPresent()) {
            com.mrattcs.instagramapi.modal.User user = oUserRepository.get();

            List<GrantedAuthority> authorities = new ArrayList<>();

            return new User(user.getEmail(), user.getPassword(), authorities);
        }

        throw new BadCredentialsException("user with username " + username + " not found");
    }
}
