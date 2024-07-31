package com.joja.api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.joja.api.models.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTService {

    @Value("${spring.dotenv.password.jwt}")
    private String password;
    public String createTokenJwt(UserModel userModel){
        try {
            var algorithm = Algorithm.HMAC256(password);
            return JWT.create()
                    .withIssuer("JoJa Supermarket")
                    .withExpiresAt(tokenTime())
                    .withSubject(userModel.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Token inválido", exception);
        }
    }

    public String tokenValidation(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            return JWT.require(algorithm)
                    .withIssuer("JoJa Supermarket")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token inválido ou expirado", exception);
        }
    }

    public Instant tokenTime(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
