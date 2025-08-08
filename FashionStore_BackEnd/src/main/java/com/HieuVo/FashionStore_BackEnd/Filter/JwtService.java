package com.HieuVo.FashionStore_BackEnd.Filter;


import com.HieuVo.FashionStore_BackEnd.Service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    //key
    public static final String SECRET_KEY = "HIEUVODEPTRAI123456789MDHAUFGUYHJFGSDFvietchonodailenchukodudaithikhongcoduocdaunha";

    private final UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    //    tạo JWT từ username
    public String generateToken(String userName, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
//        User user = this.userService.fetchUserByUsername(userName);
        return createToken(claims, userName, roles);
    }

    private String createToken(Map<String, Object> claims, String userName, List<String> roles) {
        claims.put("roles", roles);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis())) //thời gian ban hành
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //thời hạn 1 day
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Lấy secret key
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Sử dụng SECRET_KEY cố định
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
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class); // roles là List<String> nên ép kiểu
    }

}
