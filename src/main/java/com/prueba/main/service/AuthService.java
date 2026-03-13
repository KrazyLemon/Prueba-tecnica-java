package com.prueba.main.service;

import com.prueba.main.exceptions.BadRequestException;
import com.prueba.main.exceptions.InternalServerException;
import com.prueba.main.exceptions.ResourceNotFoundException;
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
        if(user.isEmpty()) throw new ResourceNotFoundException("User not found");
        if(!comparePassword(user.get().getPassword(),request.getPassword())) throw new BadRequestException("Incorrect Password");
        AuthResponse response = new AuthResponse(
                "TOKEN_EJEMPLO_DE_AUTENTICACION_AL_SISTEMA",
                "REFRESH_TOKEN_PARA_NO_HACER_LOGIN",
                "3600",
                "Bearer",
                user.get()
        );
        return Optional.of(response);
    }

    public boolean comparePassword(String storedPassword, String requestPassword)  {
        try{
            String decryptPassword = aes256.decrypt(storedPassword);
            return decryptPassword.equals(requestPassword);
        }catch (Exception e){
            throw new InternalServerException("Internal Server Erorr");
        }
    }
}
