package com.ingryd.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingryd.sms.model.LoginResponseDTO;
import com.ingryd.sms.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public LoginResponseDTO loginUser(String email, String password) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            String token = tokenService.generateJwt(auth);
            return new LoginResponseDTO(userRepository.findByEmail(email).get(), token);
            
        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, e.getMessage());
        }
    }
}
