package com.tao.userloginandauth.util;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Description TODO
 * @Author puxing
 * @Date 2025/1/20
 */
public class JdbcUtil {

    public String user;

    public JdbcUtil(String user, String password, String url, String driverClass) {
        this.user = user;
        this.password = password;
        this.url = url;
        this.driverClass = driverClass;
    }

    public String password;
    public String url;
    public String driverClass;

    public  void close() {
        try {
            if (m_connection != null) {
                m_connection.close();
                m_connection = null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public  Connection openConnection(){
        Connection connection = null;
        try {

//            String user = "SYSDBA";
//            String password = "Sysdba2024@Xugu";
////            String password = "Sysdba2023@Xugu";
//            String url = "jdbc:xugu://10.159.90.202:36034/ythpt";
////            String url = "jdbc:xugu://10.159.90.202:36034/SYSTEM";
//            String driverClass = "com.xugu.cloudjdbc.Driver";
            try {
                //加载驱动
                Class.forName(driverClass);
                //获取连接
                connection = DriverManager.getConnection(url, user, password);
                m_connection = connection;
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }
    public  Connection m_connection;
}
