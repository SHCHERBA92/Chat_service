package com.example.chat_service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;
import java.util.Date;

@Service
public class JwtUtils {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.subject}")
    private String SUBJECT;

    @Value("${jwt.issure}")
    private String ISSURE;

    public String getUserName(String token){
        var verification = JWT.require(Algorithm.HMAC256(SECRET));
        var jwtVer = verification.withSubject(SUBJECT)
                .withIssuer(ISSURE).build();
        var decodeToken = jwtVer.verify(token);
        if (decodeToken.getExpiresAt().before(new Date())){
            throw new RuntimeException("Invalid Date of Token");
        }
        return decodeToken.getClaim("username").asString();
    }
}
