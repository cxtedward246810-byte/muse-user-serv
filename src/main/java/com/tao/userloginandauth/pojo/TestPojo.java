package com.tao.userloginandauth.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author puxing
 * @Date 2025/4/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPojo {
    private Integer id;               // 主键
    private String content;
    private String name;       // 模板名称
    private String areaCode;          // 行政区划
    private String ftpUrl;            // FTP 地址
    private Integer ftpPort;           // FTP 端口
    private String ftpUserName;       // FTP 用户名
    private String ftpPassword;       // FTP 密码
    private String ftpFile;           // FTP 文件路径
    private String typeName;
    private String systemType;
    private String emails;
    private String mailTitle;
    private String mailContent;
    private String taskName;
    private String historyProductType;
}

