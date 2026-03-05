package com.tao.userloginandauth.controller;

import com.tao.userloginandauth.pojo.User;
import com.tao.userloginandauth.pojo.userDTO;
import com.tao.userloginandauth.service.RoleUserService;
import com.tao.userloginandauth.service.UserService;
import com.tao.userloginandauth.util.JwtUtil;
import com.tao.userloginandauth.vo.SysResult;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description //TODO
 * Create by 2023/7/17
 */

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/getUserMsgByToken")
    public SysResult getUserMsgByToken(@RequestParam String token) throws ClassNotFoundException {
        Claims claims = jwtUtil.verifyJwt(token);
        boolean hasUserName = claims.containsKey("userName");     // 推荐
        if (!hasUserName) {
            return SysResult.fail("token中未包含userName！！！");
        }
        boolean hasAreaCode = claims.containsKey("areaCode");
        if (!hasAreaCode) {
            return SysResult.fail("token中未包含areaCode！！！");
        }
        String userName = (String) claims.get("userName");
        String areaCode = (String) claims.get("areaCode");
        return userService.getUserList(null,null,areaCode, null, null, 10, 1,userName,null,"yes",false);
    }


    @PostMapping("/login") //测试数据 admin123 admin123  //admin admin123456
    public SysResult login(@RequestBody User user){
        return userService.login(user);
    }

//    @PostMapping("/loginByCertificate") //测试数据 admin123 admin123  //admin admin123456
//    public SysResult loginByCertificate(@RequestBody Map<String,Object> map) throws JSONException {
//        return userService.loginByCertificate(map);
//    }

    @PostMapping("/logout")
    public SysResult logout(@RequestParam("userName") String userName){
        return userService.logout(userName);
    }



    @GetMapping("/randomLenNum")
    public SysResult randomLenNum(@RequestParam("length") Integer length){
        return userService.randomLenNum(length);
    }

//    @GetMapping("/getUserInfo")
//    public SysResult getUserInfo(@RequestParam("userName") String userName){
//        return userService.getUserInfo(userName);
//    }

    @GetMapping("/deleteUserInfo")
    public SysResult deleteUserInfo(@RequestParam("userName") String userName){
        return userService.getUserInfo(userName);
    }

    @PostMapping("/updateUserInfo")
    public SysResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    public SysResult register(@RequestBody User user) throws ClassNotFoundException {
        return userService.register(user);
    }

    @GetMapping("/getAllUserInfo")
    public SysResult getAllUserInfo(){
        return userService.getAllUserInfo();
    }
    @Autowired
    private RoleUserService roleUserService;
//查询用户列表
    @GetMapping("/getUserList")
    public SysResult getUserList(@RequestParam(required = false) String departmentId,
                                 @RequestParam(required = false) String areaCode,
                                 @RequestParam(required = false) String order,
                                 @RequestParam(required = false) String sort,
                                 @RequestParam(required = false) Integer pageSize,
                                 @RequestParam(required = false) Integer currentPage,
                                 @RequestParam(required = false) String userName,
                                 @RequestParam(required = false) String showName,
                                 @RequestParam(required = false) String roleCode,
                                 @RequestParam(required = false) Boolean recursive) throws ClassNotFoundException {
        if (pageSize == null) {
            pageSize = 10;
        }
        if (currentPage == null) {
            currentPage = 1;
        }
        List<String> userIds;
        String userIdsInSql=null;
        if (roleCode!=null){
            userIds=roleUserService.queryUserIdsByRoleCode(roleCode);
            userIdsInSql = userIds.stream()
                    .map(id -> "'" + id + "'")
                    .collect(Collectors.joining(",", "(", ")"));
        }
        return userService.getUserList(userIdsInSql,departmentId, areaCode, order, sort, pageSize, currentPage,userName,showName,"yes",recursive);
    }


    @GetMapping("/getUserMsg")
    public SysResult getUserMsg(@RequestParam String userName) throws ClassNotFoundException {
        if (userName == null|| userName.isEmpty()) {
            return SysResult.success();
        }
           Integer pageSize = 10;
           Integer currentPage = 1;
           SysResult sysResult =userService.getUserList(null,null, null, null, null, pageSize, currentPage,userName,null,"no",false);
        // 取出 data
        Map<String, Object> pageVO = (Map<String, Object>) sysResult.getData();

// 从 pageVO 中取出 records
        List<userDTO> records = (List<userDTO>) pageVO.get("records");

//取出第一个用户
        userDTO first = (records == null || records.isEmpty()) ? null : records.get(0);

// 只保留想返回的字段
        Map<String, Object> userVO = null;
        if (first != null) {
            userVO = new HashMap<>();
            userVO.put("id", first.getId());
            userVO.put("departmentId", first.getDepartmentId());
            userVO.put("userName", first.getUserName());
            userVO.put("roles", first.getRoles());
        }

        return SysResult.success(userVO);

    }



    @GetMapping("/getUserShowName")
    public SysResult getUserShowName() throws ClassNotFoundException {
        Integer pageSize = 10000;
        Integer currentPage = 1;
        SysResult sysResult =userService.getUserList(null,null, null, null, null, pageSize, currentPage,null,null,"no",false);
        // 取出 data
        Map<String, Object> pageVO = (Map<String, Object>) sysResult.getData();

// 从 pageVO 中取出 records
        List<userDTO> records = (List<userDTO>) pageVO.get("records");

        List<Map<String, Object>> res=new ArrayList<>();
// 只保留想返回的字段
       Map<String, Object> userVO = null;
        for (userDTO userDTO : records) {
            if (userDTO != null) {
                userVO = new HashMap<>();
                userVO.put("id", userDTO.getId());
                userVO.put("userName", userDTO.getUserName());
                userVO.put("showName", userDTO.getShowName());
                res.add(userVO);
            }

        }

        return SysResult.success(res);

    }

    @PostMapping("/deleteUser")
    public SysResult deleteUser(@RequestParam("id") String id){
        return userService.deleteUserInfomation(id);
    }

    @PostMapping("/changePassword")
    public SysResult changePassword(HttpServletRequest request, @RequestBody Map<String, String> params){
        String token = resolveToken(request);
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        return userService.changePassword(token, oldPassword, newPassword);
    }

    @PostMapping("/resetPassword")
    public SysResult resetPassword(HttpServletRequest request, @RequestBody Map<String, String> params){
        String token = resolveToken(request);
        String userId = params.get("userId");
        return userService.resetPassword(token, userId);
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.hasText(token)) {
            return token;
        }
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

}
