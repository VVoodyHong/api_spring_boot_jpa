package com.woody.api.config.security;

import com.woody.api.common.model.StatusCode;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationInDay.accessToken}")
    private int accessTokenExpiration;

    @Value("${jwt.expirationInDay.refreshToken}")
    private int refreshTokenExpiration;

    private static final String JWT_TYPE = "jwtType";
    private static final String ACCESS_TYPE = "access";
    private static final String REFRESH_TYPE = "refresh";
    private static final String USER_INDEX = "userIdx";

    public String createAccessToken(String userIdx) {
        return generateToken(userIdx, ACCESS_TYPE, accessTokenExpiration);
    }

    public String createRefreshToken(String userIdx) {
        return generateToken(userIdx, REFRESH_TYPE, refreshTokenExpiration);
    }

    public Long getUserIdxFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.get(USER_INDEX).toString());
    }

    private String generateToken(String userIdx, String jwtType, Integer tokenExpirationInDay) {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime expiryDate = now.plusDays(tokenExpirationInDay);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpirationInDay * 1000);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JWT_TYPE, jwtType);
        claims.put(USER_INDEX, userIdx);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
//                .setIssuedAt(Timestamp.valueOf(now))
//                .setExpiration(Timestamp.valueOf(expiryDate))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public JwtValidation validateToken(String authToken) {
        JwtValidation jwtValidation = new JwtValidation();
        if(authToken == null) {
            jwtValidation.setCode(StatusCode.CODE_651);
        } else {
            try {
                Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
                jwtValidation.setSuccess(true);
                jwtValidation.setCode(StatusCode.CODE_200);
                if (claims.getBody().get(JWT_TYPE).toString().equalsIgnoreCase(ACCESS_TYPE)) {
                    jwtValidation.setAccessToken(true);
                } else if (claims.getBody().get(JWT_TYPE).toString().equalsIgnoreCase(REFRESH_TYPE)) {
                    jwtValidation.setRefreshToken(true);
                }
            } catch (SignatureException ex) {
                log.error("Invalid JWT signature\r\n");
            } catch (MalformedJwtException ex) {
                log.error("Invalid JWT\r\n");
            } catch (ExpiredJwtException ex) {
                jwtValidation.setCode(StatusCode.CODE_653);
                log.error("Expired JWT\r\n");
            } catch (UnsupportedJwtException ex) {
                log.error("Unsupported JWT\r\n");
            } catch (IllegalArgumentException ex) {
                log.error("JWT claims string is empty.\r\n");
            }
        }
        return jwtValidation;
    }
}
