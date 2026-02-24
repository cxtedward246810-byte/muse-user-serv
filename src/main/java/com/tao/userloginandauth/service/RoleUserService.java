package com.tao.userloginandauth.service;

import com.tao.userloginandauth.mapper.UserMapper;
import com.tao.userloginandauth.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class RoleUserService {


    // ================== JDBC 配置 ==================
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL =
            "jdbc:mysql://10.158.96.40:3306/db_cloudtao_wfs_qxt?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Jm3!gTz8#Rw9XpQ2";

    static {
        try {
            // 加载 JDBC 驱动
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("加载 JDBC 驱动失败", e);
        }
    }

    /**
     * 获取数据库连接
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    /**
     * 根据 roleCode 查询对应的 userId 列表
     */
    public List<String> queryUserIdsByRoleCode(String roleCode) {
        List<String> userIds = new ArrayList<>();

        String sql = "SELECT user_id FROM t_sys_role_user WHERE role_code = ?";

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, roleCode);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    userIds.add(rs.getString("user_id"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("根据 role_code 查询 user_id 失败", e);
        }

        return userIds;
    }




}