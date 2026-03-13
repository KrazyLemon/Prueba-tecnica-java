package com.prueba.main.Security;

import com.prueba.main.model.User;
import com.prueba.main.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetails implements UserDetailsService {

    private final UserRepository repository;

    public UserDetails(UserRepository repository){
        this.repository = repository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String taxId)
            throws UsernameNotFoundException {

        User user = repository.getByTaxId(taxId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getTaxId())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}