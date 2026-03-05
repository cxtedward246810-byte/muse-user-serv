package com.tao.userloginandauth.component;

import com.tao.userloginandauth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String USER_LIST_PATH = "/api/user/getUserList";

    @Autowired
    private CustomRedisTemplate customRedisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isAnonymousUserListRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            String authorization = request.getHeader("Authorization");
            if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
            }
        }

        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims;
        try {
            claims = jwtUtil.verifyJwt(token);
        } catch (Exception e) {
            throw new BadCredentialsException("token invalid: " + e.getMessage());
        }

        Object tokenObj = customRedisTemplate.opsForValue().get("token" + token);
        String userToken = tokenObj == null ? null : tokenObj.toString();

        if (!StringUtils.hasText(userToken)) {
            Object userNameObj = claims.get("userName");
            if (userNameObj != null) {
                String userName = String.valueOf(userNameObj);
                Object legacyTokenObj = customRedisTemplate.opsForValue().get("token_" + userName);
                if (legacyTokenObj != null) {
                    userToken = legacyTokenObj.toString();
                }
            }
        }

        if (!StringUtils.hasText(userToken) || !userToken.equals(token)) {
            throw new BadCredentialsException("token expired");
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAnonymousUserListRequest(HttpServletRequest request) {
        return "GET".equalsIgnoreCase(request.getMethod())
                && USER_LIST_PATH.equals(request.getServletPath());
    }
}
