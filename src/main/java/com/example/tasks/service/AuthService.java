package com.example.tasks.service;

import com.example.tasks.domain.User;
import com.example.tasks.dto.request.LoginCredentialsDTO;
import com.example.tasks.exception.InvalidCredentialsException;
import com.example.tasks.exception.ResourceNotFoundException;
import com.example.tasks.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.security.Credential;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
public class AuthService {
    private final UserRepository userRepository;

    @Value("${jwt.secret}") String jwtSecret;
    @Value("${jwt.expiration.ms}") String jwtExpiration;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(LoginCredentialsDTO loginCredentialsDTO) throws JoseException {
        //decode
        loginCredentialsDTO.setEmail(new String(Base64.getDecoder().decode(loginCredentialsDTO.getEmail())));
        loginCredentialsDTO.setPassword(new String(Base64.getDecoder().decode(loginCredentialsDTO.getPassword())));

        String hashPassword = Credential.MD5.digest(loginCredentialsDTO.getPassword()).replaceFirst("MD5:", "").toLowerCase();

        User dbUser = userRepository.findByEmail(loginCredentialsDTO.getEmail()).orElseThrow(() -> {
            log.warn("Login attempt failed for email: {}", loginCredentialsDTO.getEmail());
            return new ResourceNotFoundException(User.class, "email", loginCredentialsDTO.getEmail());
        });

        if(hashPassword.equals(dbUser.getPassword())) {
            return createJWToken(loginCredentialsDTO.getEmail());
        }

        throw new InvalidCredentialsException();
    }

    private String createJWToken(String email) throws JoseException {
        JwtClaims claims = new JwtClaims();
        claims.setIssuedAtToNow();
        claims.setExpirationTimeMinutesInTheFuture((float) Long.parseLong(jwtExpiration) / (1000 * 60));
        claims.setSubject(email);

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(new AesKey(jwtSecret.getBytes(StandardCharsets.UTF_8)));

        return jws.getCompactSerialization();
    }

}
