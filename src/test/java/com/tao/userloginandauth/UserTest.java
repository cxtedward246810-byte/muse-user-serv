package com.tao.userloginandauth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description TODO
 * @Author puxing
 * @Date 2025/6/4
 */
@SpringBootTest
public class UserTest {
    @Test
    void t(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("Ythpt@2025.");
        System.out.println(password);
    }
}

