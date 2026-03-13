package com.prueba.main.service;

import com.prueba.main.model.AuthResponse;
import com.prueba.main.model.LoginRequest;
import com.prueba.main.model.User;
import com.prueba.main.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final Aes256GcmService aes256;

    public Optional<AuthResponse> login(LoginRequest request) throws  Exception{
        Optional<User> user = userRepository.getByTaxId(request.getTaxId());
        if(user.isEmpty()) return Optional.empty();
        if(!comparePassword(user.get().getPassword(),request.getPassword())) return Optional.empty();
        AuthResponse response = new AuthResponse(
                "TOKEN_EJEMPLO_DE_AUTENTICACION_AL_SISTEMA",
                "REFRESH_TOKEN_PARA_NO_HACER_LOGIN",
                "3600",
                "Bearer",
                user.get()
        );
        return Optional.of(response);
    }

    public boolean comparePassword(String storedPassword, String requestPassword) throws Exception {
        String decryptPassword = aes256.decrypt(storedPassword);
        return decryptPassword.equals(requestPassword);
    }

}
