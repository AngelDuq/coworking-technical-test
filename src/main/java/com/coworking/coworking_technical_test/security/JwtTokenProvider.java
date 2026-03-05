package com.coworking.coworking_technical_test.security;

import com.coworking.coworking_technical_test.shared.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private SecretKey getSigningKey() {
        byte[] keyBytes = Constants.SIGNING_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genera un token JWT a partir de la autenticación del usuario.
     */
    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = System.currentTimeMillis();
        long expiration = now + (Constants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000L);

        return Jwts.builder()
                .subject(authentication.getName())
                .claim(Constants.AUTHORITIES_KEY, authorities)
                .issuer(Constants.ISSUER_TOKEN)
                .issuedAt(new Date(now))
                .expiration(new Date(expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Obtiene el nombre de usuario (subject) desde el token.
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    /**
     * Valida el token verificando firma y expiración.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene la fecha de expiración del token JWT.
     */
    public LocalDateTime getExpirationFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getExpiration().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Obtiene la autenticación a partir del token JWT.
     */
    public UsernamePasswordAuthenticationToken getAuthentication(String token, UserDetails userDetails) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String authoritiesStr = claims.get(Constants.AUTHORITIES_KEY, String.class);
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(authoritiesStr.split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }
}
