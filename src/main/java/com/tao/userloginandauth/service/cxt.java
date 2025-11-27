//package com.tao.userloginandauth.service;
//
//import com.tao.userloginandauth.mapper.UserMapper;
//import com.tao.userloginandauth.pojo.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class cxt {
//    @Autowired
//    private UserMapper userMapper;
//
//    @PostConstruct
//    public void ss() throws ClassNotFoundException {
//        String url = "jdbc:mysql://10.158.96.40:3306/db_cloudtao_wfs_qxt?useSSL=false&serverTimezone=UTC";
//        String username = "root";
//        String password = "Jm3!gTz8#Rw9XpQ2";
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        List<User> userList = userMapper.selectALLUser();
//
//        try (Connection conn = DriverManager.getConnection(url, username, password)) {
//
//            String sql = "INSERT INTO t_sys_role_user " +
//                    " (id, user_id, role_id, role_code, user_name, real_name, role_title) " +
//                    " VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//            // PreparedStatement 放在 try-with-resources 内的第二层
//            try (PreparedStatement ps = conn.prepareStatement(sql)) {
//
//                for (User user2 : userList) {
//
//                    ps.setString(1, UUID.randomUUID().toString());                 // id
//                    ps.setString(2, String.valueOf(user2.getId()));                // user_id
//                        // role_id
//
//                    ps.setString(5, user2.getUserName());                          // user_name
//                    ps.setString(6, user2.getShowName());                          // user_show_name
//
//
//                    if(user2.getAreaCode().endsWith("00")){
//                        ps.setString(3,"6ae2481f-a1db-42e3-97ae-850ec1201b8d");
//                        ps.setString(4, "role-city");
//                        ps.setString(7, "地市用户");
//                    }else {
//                        ps.setString(3, "3b85437c-ab04-4a36-a565-c73792bbd9fb");
//                        ps.setString(4,"role-county");
//                        ps.setString(7,"区县用户");
//                    }
//
//
//                    ps.addBatch(); // 批量插入
//                }
//
//                int[] rows = ps.executeBatch();
//                System.out.println("批量插入成功，共影响行数：" + rows.length);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
