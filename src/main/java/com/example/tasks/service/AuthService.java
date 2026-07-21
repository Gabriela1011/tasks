package com.example.tasks.service;

import com.example.tasks.domain.User;
import com.example.tasks.dto.request.LoginCredentialsDTO;
import com.example.tasks.exception.InvalidCredentialsException;
import com.example.tasks.exception.ResourceNotFoundException;
import com.example.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.security.Credential;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String login(LoginCredentialsDTO loginCredentialsDTO) throws JoseException {
        loginCredentialsDTO.setEmail(new String(Base64.getDecoder().decode(loginCredentialsDTO.getEmail())));
        loginCredentialsDTO.setPassword(new String(Base64.getDecoder().decode(loginCredentialsDTO.getPassword())));

        String hashPassword = Credential.MD5.digest(loginCredentialsDTO.getPassword()).replaceFirst("MD5:", "").toLowerCase();

        User dbUser = userRepository.findByEmail(loginCredentialsDTO.getEmail()).orElseThrow(() -> {
            log.warn("Login attempt failed for email: {}", loginCredentialsDTO.getEmail());
            return new ResourceNotFoundException(User.class, "email", loginCredentialsDTO.getEmail());
        });

        if (hashPassword.equals(dbUser.getPassword())) {
            return jwtService.createToken(loginCredentialsDTO.getEmail());
        }

        throw new InvalidCredentialsException();
    }
}
