package com.tao.userloginandauth.component;

import com.alibaba.fastjson.JSON;
import com.tao.userloginandauth.util.WebUtils;
import com.tao.userloginandauth.vo.SysResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description TODO  有关权限的拦截器
 * Create by 2023/7/27
 */

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        SysResult result = SysResult.fail(HttpStatus.FORBIDDEN.value(), "user-service--权限不足"); //         SysResult result =new SysResult.fail(HttpStatus.FORBIDDEN.value(), "权限不足");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);

    }
}



