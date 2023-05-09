package com.simpllyrun.srcservice.api.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Date;

@Log4j2
@Component
@NoArgsConstructor
public class JwtTokenProvider {
    private static final String JWT_SECRET = "secretKeysecretsecretsecretKeysecretsecretsecretKeysecretsecret"; //길이가 짧다는 오류 발생방지 위해 길게 함

    //토큰 유효시간
    private static final Long JWT_EXPIRATION = 1000*60*10L;

    public static String generateToken(String email){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + JWT_EXPIRATION);

        Claims claims = Jwts.claims()
                .setSubject("accessToken") //사용자
                .setIssuedAt(now) //현재 시간 기반으로 생성
                .setExpiration(expirationDate);// 만료 시간 세팅

        //private claims
        claims.put("email", email);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET) //사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
                .compact();
    }

    public static String refreshToken(){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + JWT_EXPIRATION);

        Claims claims = Jwts.claims()
                .setSubject("refreshToken") //사용자
                .setIssuedAt(now) //현재 시간 기반으로 생성
                .setExpiration(expirationDate);// 만료 시간 세팅

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    /**
     * AccessToken 헤더에 실어서 보내기
     */
    public static void sendAccessToken(HttpServletResponse response, String accessToken){
        response.setStatus(HttpServletResponse.SC_OK);

        response.addHeader("accessToken", accessToken);
        log.info("Access Token = {}", accessToken);
    }

    /**
     * AccessToken + RefreshToken 헤더에 실어서 보내기
     */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken){
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader("accessHeader", accessToken);
        response.setHeader("refreshHeader", refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }


    // Jwt 토큰에서 이메일 추출
    public static String getUserEmail(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        log.info("id={}", claims.getId());
        log.info("issuer={}", claims.getIssuer());
        log.info("issue={}", claims.getIssuedAt().toString());
        log.info("subject={}", claims.getSubject());
        log.info("Audience={}", claims.getAudience());
        log.info("expire={}", claims.getExpiration().toString());
        log.info("name={}", claims.get("name"));

        return claims.getSubject();
    }

    //토큰 유효성 + 만료일자 확인
    public static boolean validateToken(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
            return !claims.getExpiration().before(new Date());
        }catch (SignatureException e) {
            log.error("Invalid JWT signature", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
        }
        return false;
    }
}
