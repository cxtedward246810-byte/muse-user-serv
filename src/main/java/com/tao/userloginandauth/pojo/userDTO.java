package com.tao.userloginandauth.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class userDTO {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String areaCode; //行政区划代码
    private String showName;
    private String departmentId;
    private List<Map<String,Object>> roles;
}
