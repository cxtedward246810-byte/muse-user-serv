package com.tao.userloginandauth.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.connection.RedisServer;

import java.io.Serializable;
import java.util.List;

/**
 * @Description //TODO
 * Create by 2023/7/17
 */


@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String userInfo;
    private String areaCode; //行政区划代码
    private String showName;
    private List<String> role;
    private String departmentId;
//    private String role;
//    private static final long serialVersionUID = -40356785423868312L;

}
