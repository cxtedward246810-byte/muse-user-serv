package com.tao.userloginandauth.service;

import com.tao.userloginandauth.mapper.UserMapper;
import com.tao.userloginandauth.pojo.LoginUser;
import com.tao.userloginandauth.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description //TODO
 * Create by 2023/7/26
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //TODO 根据用户查询权限信息 添加到LoginUser中
//        //封装成UserDetails对象返回
//        User user = userMapper.selectByUser(username);

    /// /        HashMap<String,Object> map= userMapper.selectUserPermissions(username);
    /// /        HashMap<String,List<Integer>> map= userMapper.selectUserPermissions(username);
//        List<Integer> listRoles= userMapper.selectUserPermissions(username);
//        List<String> list = new ArrayList<>(Collections.singletonList(listRoles.toString()));
//        return new LoginUser(user,list);
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO 根据用户查询权限信息 添加到LoginUser中
        //封装成UserDetails对象返回
//        User user = userMapper.selectByUser(username);
//        List<Integer> listRoles = userMapper.selectUserPermissions(username);
        List<String> listRoles = new ArrayList();
        listRoles.add("1");
        listRoles.add("0");
        User user = new User();
        user.setUserName("username");
        return new LoginUser(user, listRoles);
    }
}

