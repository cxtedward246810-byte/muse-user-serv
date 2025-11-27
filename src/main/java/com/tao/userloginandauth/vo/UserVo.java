package com.tao.userloginandauth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description //TODO
 * Create by 2023/9/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private String password;
    private String userName;
    private String phone;
    private String code;
    private String userCode;
}
