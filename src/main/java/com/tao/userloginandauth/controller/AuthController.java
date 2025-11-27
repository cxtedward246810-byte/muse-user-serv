//package com.tao.userloginandauth.controller;
//
//import com.tao.userloginandauth.pojo.LoginUser;
//import com.tao.userloginandauth.pojo.User;
//import com.tao.userloginandauth.util.JwtUtil;
//import io.jsonwebtoken.Claims;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Objects;
//
///**
// * @Description TODO
// * @Author puxing
// * @Date 2024/9/6
// */
//@CrossOrigin
//@RestController
//@RequestMapping("/validate-token")
//public class AuthController {
//
//    @PostMapping
//    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
//        // 返回true表示Token有效，false表示无效
//        return ResponseEntity.ok(validateTokenLogic(token));
//    }
//
//    private boolean validateTokenLogic(String token) {
//        //解析token
//        User user = null;
//        try {
//            Claims claims = JwtUtil.parseJWT(token);
//            String userid = claims.getSubject();
//            user = userMapper.getUserById(userid);
//        } catch (Exception e) {
//            throw new RuntimeException("token非法: "+e.getMessage());
//        }
//        String userToken = customRedisTemplate.opsForValue().get("token_"+user.getUserName()).toString();
//        System.out.println("userToken："+userToken);
//        if (! userToken.equals(token)) {
//            throw new RuntimeException("token失效");
//        }
//        //从redis中获取用户信息
//        String redisKey = "user_" +user.getUserName();
//        LoginUser loginUser = (LoginUser) customRedisTemplate.opsForValue().get(redisKey);
//        if(Objects.isNull(loginUser)){
//            throw new RuntimeException("用户未登录");
//        }
//        //存入SecurityContextHolder
//        //TODO 获取权限信息封装到Authentication中
//        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        //放行
//        filterChain.doFilter(request, response);
//        return true; // 示例代码，实际应根据业务逻辑实现
//    }
//}
//
