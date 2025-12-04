package com.tao.userloginandauth.service;


import com.tao.userloginandauth.pojo.User;
import com.tao.userloginandauth.vo.SysResult;
import org.codehaus.jettison.json.JSONException;

import java.util.Map;

public interface UserService {
    SysResult login(User user);

    SysResult logout(String userName);

    SysResult getUserInfo(String userName);

    SysResult updateUserInfo(User user);

    SysResult register(User user) throws ClassNotFoundException;

    SysResult getAllUserInfo();

    SysResult loginByCertificate(Map<String, Object> map) throws JSONException;

    SysResult randomLenNum(Integer length);

    SysResult getUserList(String departmentId, String adminCode,
                          String order, String sort, Integer pageSize, Integer currentPage,String username,String showName,String isMoHu) throws ClassNotFoundException;

    SysResult deleteUserInfomation(String id);
}
