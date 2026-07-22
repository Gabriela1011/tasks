package com.example.tasks.service;

import com.example.tasks.domain.User;
import com.example.tasks.dto.request.LoginCredentialsDTO;
import com.example.tasks.dto.request.RegisterUserDTO;
import com.example.tasks.exception.DuplicateFieldException;
import com.example.tasks.exception.InvalidCredentialsException;
import com.example.tasks.exception.InvalidEmailFormatException;
import com.example.tasks.exception.ResourceNotFoundException;
import com.example.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.security.Credential;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String login(LoginCredentialsDTO loginCredentialsDTO) throws JoseException {
        String email = decodeBase64(loginCredentialsDTO.getEmail());
        String hashedPassword = hashPassword(decodeBase64(loginCredentialsDTO.getPassword()));

        User dbUser = userRepository.findByEmail(email).orElseThrow(() -> {
            log.warn("Login attempt failed for email: {}", email);
            return new ResourceNotFoundException(User.class, "email", email);
        });

        if (hashedPassword.equals(dbUser.getPassword())) {
            return jwtService.createToken(email);
        }

        throw new InvalidCredentialsException();
    }

    @Transactional
    public String register(RegisterUserDTO dto) throws JoseException {
        String email = decodeBase64(dto.getEmail());

        if (!email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidEmailFormatException();
        }

        String username = dto.getUsername();

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateFieldException(User.class, "email", email);
        }

        if (userRepository.existsByUsername(username)) {
            throw new DuplicateFieldException(User.class, "username", username);
        }

        User user = User.builder()
                .email(email)
                .password(hashPassword(decodeBase64(dto.getPassword())))
                .username(username)
                .birthDate(dto.getBirthDate())
                .build();

        userRepository.save(user);

        return jwtService.createToken(email);
    }

    private String decodeBase64(String value) {
        return new String(Base64.getDecoder().decode(value));
    }

    private String hashPassword(String password) {
        return Credential.MD5.digest(password).replaceFirst("MD5:", "").toLowerCase();
    }
}
