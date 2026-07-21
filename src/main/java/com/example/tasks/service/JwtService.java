package com.example.tasks.service;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Service
public class JwtService {

    private final Key signingKey;
    private final JwtConsumer jwtConsumer;
    private final long jwtExpiration;

    public JwtService(@Value("${jwt.secret}") String jwtSecret,
                      @Value("${jwt.expiration.ms}") long jwtExpiration) {

        this.jwtExpiration = jwtExpiration;
        this.signingKey = new AesKey(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setVerificationKey(signingKey)
                .build();
    }

    public String createToken(String email) throws JoseException {
        JwtClaims claims = new JwtClaims();
        claims.setIssuedAtToNow();
        claims.setExpirationTimeMinutesInTheFuture((float) jwtExpiration / (1000 * 60));
        claims.setSubject(email);

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(signingKey);

        return jws.getCompactSerialization();
    }

    public String extractEmail(String token) throws InvalidJwtException, MalformedClaimException {
        return jwtConsumer.processToClaims(token).getSubject();

        //processToClaims = parse token
                        // + verify the signature (InvalidJwtException)
                        // + expiration (InvalidJwtException)
                        // + return claims
    }
}
