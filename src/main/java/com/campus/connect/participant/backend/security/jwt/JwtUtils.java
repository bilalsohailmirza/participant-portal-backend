package com.campus.connect.participant.backend.security.jwt;

import java.security.Key;
import java.security.KeyStore.SecretKeyEntry;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.campus.connect.participant.backend.security.services.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

  public String generateJwtToken(Authentication authentication) {

    CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + 86400000))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }
  
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode("======================Hamza==Bilal==Talal==========================="));
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
               .parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
        Jwts.parserBuilder()
            .setSigningKey(key()) // Replace `key()` with your method to retrieve the secret key
            .build()
            .parseClaimsJws(authToken);
        return true; // If parsing is successful, the token is valid
    } catch (Exception e) {
        // Log the exception for debugging purposes
        System.err.println("Invalid JWT token: " + e.getMessage());
        return false; // Token is invalid
    }
}
}
