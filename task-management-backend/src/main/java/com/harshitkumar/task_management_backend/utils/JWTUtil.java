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
    private static final String REFRESH_SECRET_KEY = "django08MiniHarsh0orjgkfh08051gigafaradihfhdjghbjgfd";
    private static final long EXPIRATION_TIME = 15 * 60 * 1000; // 30 minutes
    private static final long REFRESH_KEY_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000; // 7 Days

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(SECRET_KEY)).compact();
    }

    public String generateRefreshToken(String username, String role) {
        return Jwts
                .builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_KEY_EXPIRATION_TIME))
                .signWith(getSignInKey(REFRESH_SECRET_KEY)).compact();
    }

    private SecretKey getSignInKey(String secretKey) {
        byte[] keyBytes = (byte[])Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey(SECRET_KEY)).build().parseSignedClaims(token).getPayload();
    }

    private Claims extractAllRefreshClaims(String token) {
        return Jwts.
                parser().
                verifyWith(getSignInKey(REFRESH_SECRET_KEY)).
                build().
                parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = this.extractAllClaims(token);
        return resolver.apply(claims);
    }

    public <T> T extractRefreshClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllRefreshClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUsernameFromRefreshClaim(String token) {
        return extractRefreshClaim(token, Claims::getSubject);
    }

    public String extractRoleFromRefreshClaim(String token) {
        Claims claims = extractAllRefreshClaims(token);
        return claims.get("role", String.class);
    }

    public boolean validateToken(String token, String userName) {
        try {
            String username = extractUsername(token);
            return username.equals(userName) && !isTokenExpired(token);
        } catch (Exception var4) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            return !isRefreshTokenExpired(token);
        } catch (Exception var4) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private boolean isRefreshTokenExpired(String token) {
        return extractRefreshExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Date extractRefreshExpiration(String token) {
        return extractRefreshClaim(token, Claims::getExpiration);
    }
}

