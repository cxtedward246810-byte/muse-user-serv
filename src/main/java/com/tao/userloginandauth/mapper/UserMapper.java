package com.tao.userloginandauth.mapper;


import com.tao.userloginandauth.pojo.User;
import com.tao.userloginandauth.vo.SysResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @Description //TODO
 * Create by 2023/7/17
 */

@Mapper
public interface UserMapper {
//
    List<User> selectALLUser();
    User selectByUser(String admin);
//
    List<Integer> selectUserPermissions(String userName);
//
//    User getUserByUserName(String userName);
    List<HashMap<String,Object>> getUserByUserName(String userName);
//
////    User insertUser(String userName, String passwordBC, String phone, String mail, String departName);
//    void insertUser(@Param("user")User user);
//
//    void insertRole(Long userId, int roleId);

    User getUserById(String userid);

    Boolean register(User user);

    void update(User user);

//    List<User> getAllUserInfo();
    List<HashMap<String, Object>> getAllUserInfo();

    List<HashMap<String, Object>> getUserByEmail(@Param("email") String email);

    User getUserInfoByUserName(@Param("userName") String userName);

    List<User> getUserListByConditions(@Param("departmentId") String departmentId,
                                       @Param("areaCode") String areaCode,
                                       @Param("sort") String sort,
                                       @Param("order") String order,
                                       @Param("offset") Integer offset,
                                       @Param("pageSize") Integer pageSize,
                                       @Param("userName") String userName,
                                       @Param("showName") String showName);

    void deleteUser(String id);
    Integer countUserListByConditions(@Param("departmentId") String departmentId,
                                      @Param("areaCode") String areaCode);


//    List<HashMap<String,Object>> query();
}
