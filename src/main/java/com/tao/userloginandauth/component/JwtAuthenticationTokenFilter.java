package com.tao.userloginandauth.component;

import com.tao.userloginandauth.mapper.UserMapper;
import com.tao.userloginandauth.pojo.LoginUser;
import com.tao.userloginandauth.pojo.User;
import com.tao.userloginandauth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Description TODO 请求拦截�?
 * Create by 2023/7/26
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CustomRedisTemplate customRedisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    //token认证有问题，需要修�?
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //兼容 token: xxx �?Authorization: Bearer xxx
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            String authorization = request.getHeader("Authorization");
            if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
            }
        }
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        Claims claims;
        try {
            claims = jwtUtil.verifyJwt(token);
        } catch (Exception e) {
            throw new BadCredentialsException("token非法: " + e.getMessage());
        }

        // 先按当前登录逻辑校验：登录时写入的是 key=token+jwt, value=jwt
        Object tokenObj = customRedisTemplate.opsForValue().get("token" + token);
        String userToken = tokenObj == null ? null : tokenObj.toString();

        // 兼容历史键：token_userName
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

        System.out.println("userToken: " + userToken);
        if (!StringUtils.hasText(userToken) || !userToken.equals(token)) {
            throw new BadCredentialsException("token失效");
        }
        //放行
        filterChain.doFilter(request, response);
    }
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        //获取token
//        String token = request.getHeader("token");
//        if (!StringUtils.hasText(token)) {
//            //放行
//            filterChain.doFilter(request, response);
//            return;
//        }
//        //解析token
//        User user;
//        try {
//            Claims claims = jwtUtil.verifyJwt(token);
//            String userid = claims.getSubject();
//            user = userMapper.getUserById(userid);
//        } catch (Exception e) {
//            throw new BadCredentialsException("token非法: " + e.getMessage());
//        }
//        String userToken = customRedisTemplate.opsForValue().get("token_"+user.getUserName()).toString();
//        System.out.println("userToken: " + userToken);
//        if (userToken == null || ! userToken.equals(token)) {
//            throw new BadCredentialsException("token失效");
//        }
////        //从redis中获取用户信�?
////        String redisKey = "user_" +user.getUserName();
////        LoginUser loginUser = (LoginUser) customRedisTemplate.opsForValue().get(redisKey);
////        if(Objects.isNull(loginUser)){
////            throw new RuntimeException("用户未登�?);
////        }
////        //存入SecurityContextHolder
////        //TODO 获取权限信息封装到Authentication�?
////        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
////        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        //放行
//        filterChain.doFilter(request, response);
//    }
}

