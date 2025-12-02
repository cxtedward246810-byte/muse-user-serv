package com.tao.userloginandauth.service;



import com.alibaba.fastjson.JSONObject;

import com.tao.userloginandauth.component.CustomRedisTemplate;
import com.tao.userloginandauth.mapper.UserMapper;
import com.tao.userloginandauth.pojo.LoginUser;
import com.tao.userloginandauth.pojo.User;
import com.tao.userloginandauth.pojo.userDTO;
import com.tao.userloginandauth.util.JwtUtil;
import com.tao.userloginandauth.vo.SysResult;

import io.jsonwebtoken.Claims;
import org.codehaus.jettison.json.JSONException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description //TODO
 * Create by 2023/7/17
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CustomRedisTemplate customRedisTemplate;

    @Autowired
    private JwtUtil jwtUtil;
    // MySQL 连接信息（可改成读取配置）
    String URL = "jdbc:mysql://10.158.96.40:3306/db_cloudtao_wfs_qxt?useSSL=false&serverTimezone=UTC";
    String USERNAME = "root";
    String PASSWORD = "Jm3!gTz8#Rw9XpQ2";

    @Override
    public SysResult login(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        Authentication authenticate = null;
        LoginUser loginUser = null;
        try {
                if (user.getUserName().isEmpty() || user.getPassword().isEmpty()) {
                    return SysResult.fail("登陆失败, 用户名或密码为空");
                }
                User userInfo = userMapper.getUserInfoByUserName(user.getUserName());
                if (!bCryptPasswordEncoder.matches(user.getPassword(), userInfo.getPassword()))
                    return SysResult.fail("登陆失败, 用户名或密码错误, 请重试");
    //            HashMap<String, List<Integer>> map = userMapper.selectUserPermissions(userInfo.getUserName());


            // 注册驱动（可省略，只要 classpath 下有驱动 jar）
            Class.forName("com.mysql.cj.jdbc.Driver");
            List<String> roleCodes = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                conn.setAutoCommit(false);

                String sql = "select role_code FROM " + " t_sys_role_user " + " WHERE user_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, String.valueOf(userInfo.getId()));
                    // 必须使用 executeQuery()
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            roleCodes.add(rs.getString("role_code")); // 取出每行的 role_code
                        }
                    }
                }
                conn.commit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
//                if (roleCodes.isEmpty()){
//                    //权限表未赋值
//                    return SysResult.fail("登陆失败, 账户权限不足");
//                }
                loginUser = new LoginUser(userInfo, roleCodes);

            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            // 第一位不能是 0
            sb.append(random.nextInt(9) + 1);
            // 后面 15 位可以是 0-9
            for (int i = 0; i < 15; i++) {
                sb.append(random.nextInt(10));
            }

            Map<String, Object> userInfoMap = JSONObject.parseObject(loginUser.getUser().getUserInfo(), Map.class);
            if (userInfoMap==null)
            {
                userInfoMap=new HashMap<>();
            }
            String ranDomNum = sb.toString();
            HashMap<String, Object> map = new HashMap<>();
            userInfoMap.put("userName", loginUser.getUser().getUserName());
            userInfoMap.put("areaCode", loginUser.getUser().getAreaCode());
            userInfoMap.put("roleCodes", roleCodes);
            userInfoMap.put("userId",loginUser.getUser().getId());
            map.put("userName", loginUser.getUser().getUserName());
            map.put("areaCode", loginUser.getUser().getAreaCode());
            map.put("roleCodes", roleCodes);
            map.put("userId",loginUser.getUser().getId());
            map.put("departmentId",loginUser.getUser().getDepartmentId());
            map.put("tokenId", ranDomNum);
            userInfoMap.put("departmentId",loginUser.getUser().getDepartmentId());
            String jwt = jwtUtil.generateJwt(map, (3 * 24 * 60 * 60 * 1000));
            //Redis存储信息
            customRedisTemplate.opsForValue().set("jwt:" + loginUser.getUser().getUserName() + ":"+ ranDomNum, jwt, 7, TimeUnit.DAYS);
            //前端权限控制
            customRedisTemplate.opsForValue().set("token" + jwt, jwt, 7, TimeUnit.DAYS);
            map.put("token", jwt);

            userInfoMap.put("showName", loginUser.getUser().getShowName());
            map.put("userInfo", userInfoMap);
            map.remove("tokenId");
            map.remove("userName", loginUser.getUser().getUserName());
            map.remove("areaCode", loginUser.getUser().getAreaCode());
            map.remove("roleIds", roleCodes);
            map.remove("userId",loginUser.getUser().getId());
            map.remove("departmentId",loginUser.getUser().getDepartmentId());
            return SysResult.success(map);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return SysResult.fail("登陆失败");//防止空指针异常
        } catch (ClassNotFoundException e) {
            return SysResult.fail("登陆失败");
//            throw new RuntimeException(e);
        }
    }



    @Override
    public SysResult logout(String userName) {
        customRedisTemplate.delete("user_" + userName);
        customRedisTemplate.delete("token_" + userName);
        return SysResult.success("用户登出成功");
    }

    @Override
    public SysResult getUserInfo(String userName) {
        return SysResult.success();
    }

    @Override
    public SysResult updateUserInfo(User user) {
        if (user.getPassword()!=null&&!user.getPassword().isEmpty()) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        try {
            userMapper.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.fail();
        }
        return SysResult.success();
    }

    @Override
    public SysResult register(User user) throws ClassNotFoundException {
        User user1=userMapper.selectByUser(user.getUserName());
        if (user1!=null){
            return SysResult.fail("用户"+user.getUserName()+"已存在");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("Ythpt@2024");
        user.setPassword(password);
        userMapper.register(user);
        User user2=userMapper.selectByUser(user.getUserName());

        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql = "INSERT INTO t_sys_role_user (id,user_id, role_id,role_code,user_name,real_name,role_title) VALUES (?,?, ?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,java.util.UUID.randomUUID().toString());
            ps.setString(2, String.valueOf(user2.getId()));
            ps.setString(3, "3b85437c-ab04-4a36-a565-c73792bbd9fb");
            ps.setString(4, "role-common");
            ps.setString(5, user2.getUserName());
            ps.setString(6, user2.getShowName());
            ps.setString(7, "普通用户");

            int rows = ps.executeUpdate(); // 执行

            System.out.println("插入成功，影响行数：" + rows);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return SysResult.success();
    }

    @Override
    public SysResult getAllUserInfo() {
//        List<User> list = userMapper.getAllUserInfo();
        List<HashMap<String, Object>> list = userMapper.getAllUserInfo();
//        for (User user : list) {
//            System.out.println("user: " + user);
//            user.setPassword(null);
//        }
        for (HashMap<String, Object> hashMap : list) {
            hashMap.put("password", null);
        }
        return SysResult.success(list);
    }

    @Override
    public SysResult loginByCertificate(Map<String, Object> map) throws JSONException {
//        String certinfo = App.detachverifysign("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", map.get("signdata").toString(), map.get("plain").toString());
//        String subjectdn = App.decodeFromBase64(new JSONObject(certinfo).getJSONObject("data").getJSONObject("certinfo").getString("subjectdn"));
//        String[] split = subjectdn.split(",");
//        String email = null;
//        for (String str : split) {
//            if (str.charAt(0) == 'E') {
//                email = str.split("=")[1];
//            }
//        }
//        if (email == null) {
//            return SysResult.fail("登陆失败，解析证书主题错误");
//        }
//        email = App.encry(email);
//        List<HashMap<String, Object>> list = userMapper.getUserByEmail(email);
//        if (list == null || list.isEmpty())
//            return SysResult.fail("登陆失败，账户邮箱不匹配");
//        List<String> excludeParamsList = new ArrayList<>();
//        excludeParamsList.add("id");
//        HashMap<String, Object> userInfo = App.decryptBatch(list, excludeParamsList).get(0);
//        HashMap<String, Object> userInfoMap = new HashMap<>();
//        userInfoMap.put("userName", userInfo.get("userName").toString());
//        userInfoMap.put("realName", userInfo.get("realName").toString());
//        userInfoMap.put("adminCode", userInfo.get("adminCode").toString());
//        String jwt = jwtUtil.generateJwt(userInfoMap, (3 * 24 * 60 * 60 * 1000));
//        //Redis存储信息
//        customRedisTemplate.opsForValue().set("token_" + userInfo.get("userName").toString(), jwt, 7, TimeUnit.DAYS);
//        //前端权限控制
//        customRedisTemplate.opsForValue().set("token" + jwt, jwt, 7, TimeUnit.DAYS);
//        userInfoMap.put("token", jwt);
//        if (userInfo.get("userInfo") == null) {
//            userInfoMap.put("userInfo", null);
//        } else {
//            userInfoMap.put("userInfo", userInfo.get("userInfo").toString());
//        }
//        return SysResult.success(userInfoMap);
        return SysResult.success();
    }

    @Override
    public SysResult randomLenNum(Integer length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return SysResult.success(sb.toString());
    }

    @Override
    public SysResult getUserList(String departmentId, String adminCode,
                                 String order, String sort, Integer pageSize, Integer currentPage,
                                 String userName, String showName) throws ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        int offset = (currentPage - 1) * pageSize;

        if (sort == null || sort.trim().isEmpty()) sort = "id";
        if (!"asc".equalsIgnoreCase(order) && !"desc".equalsIgnoreCase(order)) order = "asc";

        if (adminCode != null && !adminCode.isEmpty()) {
            if (adminCode.endsWith("00")) {
                adminCode = adminCode.substring(0, adminCode.length() - 2);
            }
            adminCode = "%" + adminCode + "%";
        }

        if (userName != null && !userName.isEmpty()) {
            userName = "%" + userName + "%";
            System.out.println(userName);
        }
        if (showName != null && !showName.isEmpty()) {
            showName = "%" + showName + "%";
            System.out.println(showName);
        }

        try {
            List<User> userList;

            // ====================================================
            //  departmentId 为空 → 直接查
            // ====================================================
            if (departmentId == null || departmentId.trim().isEmpty()) {

                userList = userMapper.getUserListByConditions(
                        null, adminCode, sort, order, offset, pageSize, userName, showName);

            } else {

                /* ---------------------------------------------------------
                 *   ① 先 BFS 查子部门（使用同一 Connection）
                 * --------------------------------------------------------- */
                List<String> allDeptIds = new ArrayList<>();
                Queue<String> queue = new LinkedList<>();
                queue.add(departmentId);
                allDeptIds.add(departmentId);

                String sqlFindChildren = "SELECT id FROM t_sys_department WHERE parent_id = ?";

                try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                     PreparedStatement psDept = conn.prepareStatement(sqlFindChildren)) {

                    while (!queue.isEmpty()) {
                        String pid = queue.poll();

                        psDept.setString(1, pid);
                        try (ResultSet rs = psDept.executeQuery()) {
                            while (rs.next()) {
                                String childId = rs.getString("id");
                                allDeptIds.add(childId);
                                queue.add(childId);
                            }
                        }
                    }

                    /* ---------------------------------------------------------
                     *   ② 拼接 ('id1','id2') 格式
                     * --------------------------------------------------------- */
                    String deptIdSql = allDeptIds.stream()
                            .map(id -> "'" + id + "'")
                            .collect(Collectors.joining(",", "(", ")"));

                    /* ---------------------------------------------------------
                     *   ③ 查询用户（此处仍使用 MyBatis）
                     * --------------------------------------------------------- */
                    userList = userMapper.getUserListByConditions(
                            deptIdSql, adminCode, sort, order, offset, pageSize, userName, showName);


                    /* ---------------------------------------------------------
                     *   ④ 查角色（复用同一个 JDBC Connection）
                     * --------------------------------------------------------- */
                    String sqlRole = "SELECT role_code, role_id, role_title, user_id FROM t_sys_role_user WHERE user_id = ?";
                    try (PreparedStatement psRole = conn.prepareStatement(sqlRole)) {

                        List<userDTO> dtoList = new ArrayList<>();

                        for (User user : userList) {
                            userDTO dto = new userDTO();
                            dto.setId(user.getId());
                            dto.setUserName(user.getUserName());
                            dto.setPassword(user.getPassword());
                            dto.setDepartmentId(user.getDepartmentId());
                            dto.setEmail(user.getEmail());
                            dto.setPhone(user.getPhone());
                            dto.setAreaCode(user.getAreaCode());
                            dto.setShowName(user.getShowName());

                            // 查角色
                            psRole.setString(1, String.valueOf(user.getId()));
                            try (ResultSet rs = psRole.executeQuery()) {
                                List<Map<String, Object>> roles = new ArrayList<>();
                                while (rs.next()) {
                                    Map<String, Object> item = new HashMap<>();
                                    item.put("roleCode", rs.getString("role_code"));
                                    item.put("roleId", rs.getString("role_id"));
                                    item.put("roleTitle", rs.getString("role_title"));
                                    item.put("userId", rs.getString("user_id"));
                                    roles.add(item);
                                }
                                dto.setRoles(roles);
                            }

                            dtoList.add(dto);
                        }

                        // ⑤ 查总数
                        Integer total = userMapper.countUserListByConditions(deptIdSql, adminCode);

                        // ⑥ 分页结果
                        Map<String, Object> pageVO = new HashMap<>();
                        pageVO.put("records", dtoList);
                        pageVO.put("total", total);
                        pageVO.put("current", currentPage);
                        pageVO.put("pageSize", pageSize);

                        return SysResult.success(pageVO);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return SysResult.fail("数据库异常", false);
                }
            }

            // departmentId 为空的情况补齐角色查询
            List<userDTO> dtoList = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement psRole = conn.prepareStatement(
                         "SELECT role_code, role_id, role_title, user_id FROM t_sys_role_user WHERE user_id = ?")) {

                for (User user : userList) {

                    userDTO dto = new userDTO();
                    dto.setId(user.getId());
                    dto.setUserName(user.getUserName());
                    dto.setPassword(user.getPassword());
                    dto.setDepartmentId(user.getDepartmentId());
                    dto.setEmail(user.getEmail());
                    dto.setPhone(user.getPhone());
                    dto.setAreaCode(user.getAreaCode());
                    dto.setShowName(user.getShowName());

                    psRole.setString(1, String.valueOf(user.getId()));
                    try (ResultSet rs = psRole.executeQuery()) {
                        List<Map<String, Object>> roles = new ArrayList<>();
                        while (rs.next()) {
                            Map<String, Object> item = new HashMap<>();
                            item.put("roleCode", rs.getString("role_code"));
                            item.put("roleId", rs.getString("role_id"));
                            item.put("roleTitle", rs.getString("role_title"));
                            item.put("userId", rs.getString("user_id"));
                            roles.add(item);
                        }
                        dto.setRoles(roles);
                    }

                    dtoList.add(dto);
                }
            }

            Integer total = userMapper.countUserListByConditions(null, adminCode);

            Map<String, Object> pageVO = new HashMap<>();
            pageVO.put("records", dtoList);
            pageVO.put("total", total);
            pageVO.put("current", currentPage);
            pageVO.put("pageSize", pageSize);

            return SysResult.success(pageVO);

        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.fail("数据库异常", false);
        }
    }



    //-----------------------------------------------------------------------------------------------------

    @Override
    @Transactional
    public SysResult deleteUserInfomation(String id) {
        try {
            // 从虚谷数据库查询用户信息
            User user = userMapper.getUserById(id);
            if (user == null) {
                return SysResult.fail("用户不存在", false);
            }
            if ("root".equalsIgnoreCase(user.getUserName()) || "admin".equalsIgnoreCase(user.getUserName())) {
                return SysResult.fail("无权删除", false);
            }

            // 删除虚谷数据库用户
            userMapper.deleteUser(id);
            System.out.println("虚谷表中用户 " + id + " 已删除。");

            // 删除 MySQL 中关联数据（使用原生 JDBC）
            String[] tables = {
                    "t_sys_role_user",
                    "t_sys_user_menu",
                    "t_sys_user_platform"
            };

            // MySQL 连接信息（可改成读取配置）
            String url = "jdbc:mysql://10.158.96.40:3306/db_cloudtao_wfs_qxt?useSSL=false&serverTimezone=UTC";
            String username = "root";
            String password = "Jm3!gTz8#Rw9XpQ2";

            // 注册驱动（可省略，只要 classpath 下有驱动 jar）
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                conn.setAutoCommit(false); // 保证 MySQL 部分三表删除原子性

                for (String table : tables) {
                    String sql = "DELETE FROM " + table + " WHERE user_id = ?";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, id);
                        int rows = ps.executeUpdate();
                        System.out.printf("MySQL表 %s 删除 user_id=%s 的记录，共 %d 行%n", table, id, rows);
                    }
                }

                conn.commit();
                System.out.println("MySQL 用户表中 " + id + " 关联数据清理完成。");
            } catch (Exception ex) {
                ex.printStackTrace();
                // MySQL 操作失败不影响虚谷提交（如需强事务，可手动抛出异常）
                System.err.println("MySQL 清理失败：" + ex.getMessage());
            }

            return SysResult.success("删除成功", true);

        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.fail("删除失败：" + e.getMessage(), false);
        }
    }
}
