package com.harshitkumar.task_management_backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    private static final String SECRET_KEY = "HarshMini051008MiniHarsh051008Mini051008051008";
    private static final long EXPIRATION_TIME = 86400000L;

    public JWTUtil() {
    }

    public String generateToken(String username) {
        return Jwts.builder().subject(username).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + 86400000L)).signWith(this.getSignInKey()).compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = (byte[])Decoders.BASE64.decode("HarshMini051008MiniHarsh051008Mini051008051008");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return (Claims)Jwts.parser().verifyWith(this.getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = this.extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token) {
        return (String)this.extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token, String userName) {
        try {
            String username = this.extractUsername(token);
            return username.equals(userName) && !this.isTokenExpired(token);
        } catch (Exception var4) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return (Date)this.extractClaim(token, Claims::getExpiration);
    }
}

