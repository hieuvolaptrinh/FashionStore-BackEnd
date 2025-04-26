package com.HieuVo.FashionStore_BackEnd.Filter;

import com.HieuVo.FashionStore_BackEnd.DTO.Response.RestResponse;
import com.HieuVo.FashionStore_BackEnd.Service.JwtService;
import com.HieuVo.FashionStore_BackEnd.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            System.out.println("Token: " + token);
            System.out.println("Username: " + username);
            System.out.println("AuthHeader: " + authHeader);
            System.out.println("SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication());
            filterChain.doFilter(request, response);
        } catch (SignatureException e) {
            handleJwtError(response, "JWT signature không hợp lệ", "Token không hợp lệ (chữ ký không khớp)");
        } catch (ExpiredJwtException e) {
            handleJwtError(response, "JWT đã hết hạn", "Token đã hết hạn");
        } catch (MalformedJwtException e) {
            handleJwtError(response, "JWT không đúng định dạng", "Token không đúng định dạng");
        } catch (Exception e) {
            handleJwtError(response, e.getMessage(), "Lỗi xác thực token");
        }
    }

    private void handleJwtError(HttpServletResponse response, String error, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        RestResponse<Object> res = new RestResponse<>();
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setError(error);
        res.setMessage(message);

        response.getWriter().write(objectMapper.writeValueAsString(res));
    }
}
