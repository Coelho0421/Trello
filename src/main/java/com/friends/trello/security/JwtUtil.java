package com.friends.trello.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

//Essa Classe faz 3 coisas: gera token, lê token e valida token.
@Component
public class JwtUtil {

    private String secret = "sua-chave-super-secreta-com-32-caracteres-minimo";

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generatedToken(String email){
        return Jwts.builder()
                .setSubject(email)                  // quem é o dono do token
                .setIssuedAt(new Date())            // quando foi criado
                .setExpiration(new Date())          //24h
                .signWith(getKey())                 // assina com a chave secreta
                .compact();                         // gera a string final
    }
    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())   // usa a mesma chave pra descriptografar
                .build()
                .parseClaimsJws(token)     // lê e valida o token
                .getBody()
                .getSubject();             // retorna o email que estava dentro
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername());
    }
}
