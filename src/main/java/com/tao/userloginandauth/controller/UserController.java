package com.tao.userloginandauth.controller;

import com.tao.userloginandauth.pojo.User;
import com.tao.userloginandauth.service.UserService;
import com.tao.userloginandauth.util.JwtUtil;
import com.tao.userloginandauth.vo.SysResult;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping("/test")
    public void test() {
        Claims claims = jwtUtil.verifyJwt("eyJhbGciOiJSUzI1NiJ9.eyJhcmVhQ29kZSI6IjQ1MDAwMCIsInRva2VuSWQiOiIxNzcyMzU4MTA3MTAzMzM0IiwiZGVwYXJ0bWVudElkIjoiMWJhNzllOTAtZDc2OS00ZDc2LWJlZGMtYWJlNTUwMzFlNzkzIiwicm9sZUNvZGVzIjpbInJvbGUtYWRtaW4iLCJyb2xlLWNvbW1vbiIsInJvbGUtbG93Y29kZSJdLCJ1c2VyTmFtZSI6ImFkbWluIiwidXNlcklkIjoxLCJpYXQiOjE3NjMxODY5ODgsImV4cCI6MTc2MzQ0NjE4OH0.TqdxQeSu8tITzGIaBvJe3wia7Nfh1opV9eilYlrTTuaBmWCJfOD4diV3nseAgBgD_5FQABs41JHH9Sv9euda_lXCm_f6LxTsOwP_psRdL9wtqVA8C_iOC7AAgjRw5OPmPQzOF8hmorkW_cjXRol71ijGsV2QRG_9qEOcYQ0pgeOcaO1rE8Ih0pz1VxEcLZtGQnNNcTdgdWFbzdEAIP92k2pCw98K7UjNK6ultgc97keu-_O1iQSS10uOR5WXyPJa8HtVaz41unJsNT2VPiLWJwfkfhhL4vT4YzguqVoDO74eMgbOjiQqKJ-egNAfeaEDjKshtSDiYqGEbO123l7ADg");
        boolean hasDeptId = claims.containsKey("departmentId");     // 推荐
        Object deptId = claims.get("departmentId");
        System.out.println(hasDeptId);
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

//查询用户列表
    @GetMapping("/getUserList")
    public SysResult getUserList(@RequestParam(required = false) String departmentId,
                                 @RequestParam(required = false) String areaCode,
                                 @RequestParam(required = false) String order,
                                 @RequestParam(required = false) String sort,
                                 @RequestParam(required = false) Integer pageSize,
                                 @RequestParam(required = false) Integer currentPage,
                                 @RequestParam(required = false) String userName,
                                 @RequestParam(required = false) String showName) throws ClassNotFoundException {
        if (pageSize == null) {
            pageSize = 10;
        }
        if (currentPage == null) {
            currentPage = 1;
        }
        return userService.getUserList(departmentId, areaCode, order, sort, pageSize, currentPage,userName,showName);
    }


    @PostMapping("/deleteUser")
    public SysResult deleteUser(@RequestParam("id") String id){
        return userService.deleteUserInfomation(id);
    }

}
