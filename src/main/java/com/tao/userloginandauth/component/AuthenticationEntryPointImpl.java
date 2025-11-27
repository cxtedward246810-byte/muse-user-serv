package com.tao.userloginandauth.component;

/**
 * @Description //TODO
 * Create by 2023/7/27
 */

import com.alibaba.fastjson.JSON;
import com.tao.userloginandauth.util.WebUtils;
import com.tao.userloginandauth.vo.SysResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description TODO  有关token的拦截器
 * Create by 2023/7/27
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        SysResult result =  SysResult.fail(HttpStatus.UNAUTHORIZED.value(), "user-service-token认证失败");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}



