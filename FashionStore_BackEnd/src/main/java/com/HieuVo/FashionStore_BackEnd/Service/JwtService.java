package com.HieuVo.FashionStore_BackEnd.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    //khóa
    public static final String SECRET_KEY = "@ASSSSSSSSSSSSSS%%$$$$HIEUVODZ9999";

    //    tạo JWT từ username
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
//        claims.put("isAdmin",true);
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis())) //thời gian ban hành
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) //thời hạn 30 phút
                .signWith(SignatureAlgorithm.HS256, getSignKey())
//                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    //    lấy serect key
    private Key getSignKey() {
        // Sử dụng phương thức secretKeyFor() để tạo khóa tự động có độ dài 256 bits
        return Keys.secretKeyFor(SignatureAlgorithm.HS256); // Dùng HMAC-SHA256 (HS256)

    }

    //    trích xuất thông tin
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //    trích xuất thông tin cho 1 claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);

        return claimsTFunction.apply(claims);
    }
//    kiểm tra thời gian hết hạn từ JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); //lấy ra thời gian hết hạn
    }

//    kiểm tra JWT đã hết hạn chưa ?
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName=extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }
}
