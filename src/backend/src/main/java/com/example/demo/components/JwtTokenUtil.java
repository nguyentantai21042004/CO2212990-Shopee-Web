package com.example.demo.components;

import com.example.demo.exceptions.InvalidParamException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(com.example.demo.models.users.User user) throws Exception {
        Map<String, Object> claims = new HashMap<>();

        claims.put("phoneNumber", user.getPhoneNumber());
        claims.put("email", user.getEmail());
        claims.put("password", user.getPassword());

        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                    .compact();

            return token;
        } catch (Exception e){
            throw new InvalidParamException("Cannot create jwt token, error" + e.getMessage());
        }
    }

    /* This function is used for creating a SIGNATURE from project's secret Key */
    private Key getSignatureKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);   // Encode from secretKey to bytes arr => fit with input of hmacShaKeyFor
        return Keys.hmacShaKeyFor(bytes);                   // Using Algorithm HMAC-SHA256
    }

    /* These 2 functions are used for get information from existing Key */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenExpired(String token){
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
}
