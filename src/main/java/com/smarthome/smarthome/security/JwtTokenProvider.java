package com.smarthome.smarthome.security;

import com.smarthome.smarthome.error.AuthenticationError;
import com.smarthome.smarthome.exception.ExceptionFactory;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    public String generateToken(Authentication authentication) throws UnsupportedEncodingException {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes("UTF-8"))
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (UnsupportedEncodingException e) {
            throw ExceptionFactory.create(AuthenticationError.UNSUPPORTED_JWT_TOKEN);
        }

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret.getBytes("UTF-8")).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException ex) {
            throw ExceptionFactory.create(AuthenticationError.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException ex) {
            throw ExceptionFactory.create(AuthenticationError.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException | UnsupportedEncodingException ex) {
            throw ExceptionFactory.create(AuthenticationError.UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException ex) {
            throw ExceptionFactory.create(AuthenticationError.EMPTY_JWT_TOKEN);
        }
    }
}
