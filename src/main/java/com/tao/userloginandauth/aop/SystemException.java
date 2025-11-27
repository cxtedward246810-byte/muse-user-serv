package com.tao.userloginandauth.aop;

import com.tao.userloginandauth.vo.SysResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SystemException {

    @ExceptionHandler(RuntimeException.class)
    public SysResult exception(Exception exception){
        log.error(exception.getMessage());
        exception.printStackTrace();
        return SysResult.fail();
    }
}
