package com.language_learning_progress_tracker.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.language_learning_progress_tracker.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;



import java.util.Date;

@Service
public class TokenGenerator {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        Date currentDate = new Date();
        Date expirateDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withSubject("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(expirateDate)
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            return null;
        }
    }
}
