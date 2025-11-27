//package com.tao.userloginandauth;
//
//import com.tao.userloginandauth.component.CustomRedisTemplate;
//import com.tao.userloginandauth.mapper.CASAMapper;
//import com.tao.userloginandauth.mapper.UserMapper;
//import com.tao.userloginandauth.util.JdbcUtil;
//import com.tao.userloginandauth.util.JwtUtil;
//import io.jsonwebtoken.Claims;
//import okhttp3.*;
//import org.codehaus.jettison.json.JSONArray;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import javax.net.ssl.*;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.security.cert.X509Certificate;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
//@SpringBootTest
//class JMTests {
//
//    @Autowired
//    private CASAMapper testMapper;
//
////    @Test
////    void encry() {
//////        List<HashMap<String, Object>> list = testMapper.selectData();
//////        List<HashMap<String, Object>> list = testMapper.selectStaInfoData();
////        List<HashMap<String, Object>> list = testMapper.selectGisPolygon();
////        for (HashMap<String, Object> hashMap : list) {
////            createSymmetryKey("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", hashMap);
////        }
////    }
//
//    @Test
//    void decrypt() {
//        List<HashMap<String, Object>> list = testMapper.selectEncryData();
//        for (HashMap<String, Object> hashMap : list) {
//            decryptSymmetryKey("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", hashMap);
//        }
//    }
////
////    @Test
////    void encryBatch() {
//////        List<HashMap<String, Object>> list = testMapper.selectData();
//////        List<HashMap<String, Object>> list = testMapper.selectStaInfoData();
////        List<HashMap<String, Object>> list = testMapper.selectGisPolygon();
////        for (HashMap<String, Object> hashMap : list) {
////            createSymmetryKeyBatch("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", hashMap);
////        }
////    }
//
//    @Test
//    void decryptBatch() {
//        List<HashMap<String, Object>> list = testMapper.selectEncryData();
//        for (HashMap<String, Object> hashMap : list) {
//            decryptSymmetryKeyBatch("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", hashMap);
//        }
//    }
//
//
////    //todo 加密
////    public static void createSymmetryKey(String token, HashMap<String, Object> hashMap) {
////        try {
////            final TrustManager[] trustAllCerts = new TrustManager[]{
////                    new X509TrustManager() {
////                        @Override
////                        public X509Certificate[] getAcceptedIssuers() {
////                            return new X509Certificate[0];
////                        }
////
////                        @Override
////                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
////                        }
////
////                        @Override
////                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
////                        }
////                    }
////            };
////
////            final SSLContext sslContext = SSLContext.getInstance("TLS");
////            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
////            final HostnameVerifier allHostsValid = (hostname, session) -> true;
////
////            OkHttpClient.Builder builder = new OkHttpClient.Builder();
////            Method method = builder.getClass().getMethod("sslSocketFactory", SSLSocketFactory.class, X509TrustManager.class);
////            method.invoke(builder, sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
////
////            OkHttpClient client = builder.hostnameVerifier(allHostsValid).build();
////
////            Set<String> strings = hashMap.keySet();
////            StringBuilder sb = new StringBuilder();
////            for (String key : strings) {
////                if (!key.equalsIgnoreCase("id")) {
////                    if (key.equalsIgnoreCase("userinfo")) {
////                        continue;
////                    }
////                    if (sb.length() == 0) {
////                        sb.append(String.format("\"%s\":\"%s\"", key, encodeToBase64(hashMap.get(key).toString())));
////                    } else {
////                        sb.append(",").append(String.format("\"%s\":\"%s\"", key, encodeToBase64(hashMap.get(key).toString())));
////                    }
////                }
////            }
////            String json = String.format("{\"keyCode\": \"%s\", \"algorithmParam\": \"%s\", \"data\": {%s}}",
////                    "9e724631d0de4ac0805f796a23271e07", "SM2", sb.toString().replaceAll("\"\\{", "{").replaceAll("}\"", "}"));
////            RequestBody body = RequestBody.create(
////                    MediaType.parse("application/json"),
////                    json
////            );
////
////            System.out.println("Request Body: " + json);
////            Request request = new Request.Builder()
////                    .url("https://10.158.62.154:15443/api/v1/cipher/json/encrypt")
////                    .addHeader("Content-Type", "application/json")
////                    .addHeader("Authorization", "Basic " + token)
////                    .post(body)
////                    .build();
////            System.out.println("Request URL: " + request.url());
////            System.out.println("Request Headers:");
////            for (String headerName : request.headers().names()) {
////                System.out.println(headerName + ": " + request.header(headerName));
////            }
////            String strResponse = null;
////            try (Response response = client.newCall(request).execute()) {
////                if (response.isSuccessful() && response.body() != null) {
////                    strResponse = response.body().string();
////                    System.out.println("Response: " + strResponse);
////                    try {
////                        JSONObject jsonObject = new JSONObject(strResponse)
////                                .getJSONObject("data")
////                                .getJSONObject("encData");
////                        Iterator<String> keys = jsonObject.keys();
////                        List<String> fields = new ArrayList<>();
////                        List<Object> values = new ArrayList<>();
////                        while (keys.hasNext()) {
////                            String key = keys.next();
////                            String value = jsonObject.getString(key);
////                            fields.add(key);
////                            values.add(value);
////                        }
////                        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO t_sea_gis_polygon_encry (");
////                        StringBuilder placeholders = new StringBuilder("VALUES (");
////                        for (int i = 0; i < fields.size(); i++) {
////                            if (i > 0) {
////                                sqlBuilder.append(", ");
////                                placeholders.append(", ");
////                            }
////                            sqlBuilder.append(fields.get(i));
////                            placeholders.append("?");
////                        }
////
////                        sqlBuilder.append(") ").append(placeholders).append(")");
////                        String sql = sqlBuilder.toString();
////                        Connection connection = JdbcUtil.openConnection();
////                        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
////                            for (int i = 0; i < values.size(); i++) {
////                                preparedStatement.setObject(i + 1, values.get(i));
////                            }
////                            preparedStatement.addBatch();
////                            preparedStatement.executeBatch();
////                        }
////                        System.out.println("数据入库成功!");
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    } finally {
////                        JdbcUtil.close();
////                    }
////                } else {
////                    System.out.println("Request failed with code: " + response.code());
////                    if (response.body() != null) {
////                        System.out.println("Response: " + strResponse);
////                    }
////                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////
//
//    @Test
//    void createEncryHasCLOB() throws IOException, JSONException {
////        BufferedReader br = new BufferedReader(new FileReader("C:/Users/86155/Desktop/12.json"));
////        StringBuilder sb = new StringBuilder();
////        String line;
////        while ((line = br.readLine()) != null) {
////            sb.append(line);
////        }
//
////        List<HashMap<String, Object>> list = new ArrayList<>();
////        JSONArray jsonArray = new JSONObject(sb.toString()).getJSONArray("DS");
////
////        for (int i = 0; i < jsonArray.length(); i++) {
////            JSONObject jsonObject = jsonArray.getJSONObject(i);
////            Iterator<String> keys = jsonObject.keys(); // 明确泛型为 String
////
////            HashMap<String, Object> hashMap = new HashMap<>();
////            while (keys.hasNext()) {
////                String key = keys.next(); // 正确获取 key
////                Object value = jsonObject.get(key); // 正确获取 value
////                hashMap.put(key, value);
////            }
////
////            list.add(hashMap);
////        }
////
////        System.out.println(list);
////        List<HashMap<String, Object>> list = testMapper.selectData();
////        List<HashMap<String, Object>> list = testMapper.selectStaInfoData();
////        List<HashMap<String, Object>> list = testMapper.selectMATERIAL();
////        for (HashMap<String, Object> hashMap : list) {
////            createSymmetryKeyHasCLOB("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", hashMap);
////        }
//        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
//        HashMap<String, Object> hashMap = new HashMap<>();
////        hashMap.put("key","{             \"departID\": 1,             \"areaID\": 1,             \"departName\": \"广西\",             \"parentID\": 0,             \"departCode\": \"45\",             \"departLevel\": 0,             \"codeOfTownForecast\": \"BENN\",             \"codeOfGuidanceForecast\": \"NN\",             \"codeOfDL\": \"GXDL\",             \"userName\": \"guangxi\",             \"id\": \"guangxi-uuid\",             \"name\": \"guangxi\",             \"post\": \"短临岗\",             \"showName\": \"广西气象台\",             \"areaCode\": 450000,             \"postList\": [                 {                     \"value\": \"短临岗\",                     \"label\": \"短临岗\"                 },                 {                     \"value\": \"中短期岗\",                     \"label\": \"中短期岗\"                 },                 {                     \"value\": \"决策服务岗\",                     \"label\": \"决策服务岗\"                 },                 {                     \"value\": \"地市岗\",                     \"label\": \"地市岗\"                 }             ]         }");
//        hashMap.put("key","$2a$10$TFFMXYZmNdviQ7TzIyLCWePSUNH2gwQKi4Jkn5CIu2Tf63PSAgpYS");
//        list.add(hashMap);
//        ArrayList<String> list1 = new ArrayList<>();
//        list1.add("DUTYTIME");
////        createSymmetryKeyHasCLOB("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", list, list1, "SYSDBA", "Sysdba2023@Xugu", "jdbc:xugu://10.159.90.202:36034/SYSTEM", "com.xugu.cloudjdbc.Driver", "t_station_info_encry");
////        UserApplicationTests.encryBatch("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", list, list1, "SYSDBA", "Sysdba2023@Xugu", "jdbc:xugu://10.159.90.201:31239/GRIDFSTDB", "com.xugu.cloudjdbc.Driver", "T_FTP_ENCRY");
////        UserApplicationTests.encryBatch("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", list, list1, "SYSDBA", "Sysdba2023@Xugu", "jdbc:xugu://10.159.90.201:31239/GRIDFSTDB", "com.xugu.cloudjdbc.Driver", "T_DUTYSCHEDULE_ENCRY");
////        createSymmetryKeyHasCLOB("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", list, list1, "SYSDBA", "Sysdba2024@Xugu", "jdbc:xugu://10.159.90.202:36034/ythpt", "com.xugu.cloudjdbc.Driver", "t_laibin_disaster_point_encry");
//        createSymmetryKeyHasCLOB("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", list, list1, "SYSDBA", "Sysdba2024@Xugu", "jdbc:xugu://10.159.90.202:36034/ythpt", "com.xugu.cloudjdbc.Driver", null);
//
//    }
//
//
//    /***
//     * @param token 密匙 默认为aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==
//     * @param encryptList 需要加密的数据
//     * @param excludeParamsList 需要排除的加密数据
//     * @param userDB 数据库用户名
//     * @param pwdDB 数据库密码
//     * @param urlDB 数据库链接
//     * @param driverDB 数据路驱动名
//     * @return void
//     * @author xp
//     * @date 2025/4/22 16:31
//     * @description 加密
//     */
//    public static void createSymmetryKeyHasCLOB(String token, List<HashMap<String, Object>> encryptList, List<String> excludeParamsList, String userDB, String pwdDB, String urlDB, String driverDB, String tableDB) {
//        try {
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return new X509Certificate[0];
//                        }
//
//                        @Override
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        }
//                    }
//            };
//            final SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            final HostnameVerifier allHostsValid = (hostname, session) -> true;
//
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.readTimeout(1, TimeUnit.MINUTES);
//            Method method = builder.getClass().getMethod("sslSocketFactory", SSLSocketFactory.class, X509TrustManager.class);
//            method.invoke(builder, sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
//
//            OkHttpClient client = builder.hostnameVerifier(allHostsValid).build();
//            JdbcUtil jdbcUtil = new JdbcUtil(userDB, pwdDB, urlDB, driverDB);
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < encryptList.size(); i++) {
//                HashMap<String, Object> hashMap = encryptList.get(i);
//                Set<String> keySet = hashMap.keySet();
//                if (i == 0) {
//                    sb.append("{");
//                } else {
//                    sb.append(",{");
//                }
//                for (String key : keySet) {
//                    if (excludeParamsList != null && !excludeParamsList.contains(key)) {
//                        if (hashMap.get(key) == null || hashMap.get(key).equals("")) {
//                            continue;
//                        }
//
//                        sb.append(String.format("\"%s\":\"%s\",", key, encodeToBase64(hashMap.get(key).toString())));
//
//                    }
//                }
//                sb.append("}");
//            }
//
//            String json = String.format("{\"keyCode\": \"%s\", \"algorithmParam\": \"%s\", \"data\": [%s]}",
//                    "70592de7d3064c3f86cabd8244082832", "SM4/ECB/PKCS7Padding", sb.toString().replaceAll("\"\\{", "{").replaceAll("}\"", "}").replaceAll("\\{,", "{").replaceAll(",}", "}"));
//            RequestBody body = RequestBody.create(
//                    MediaType.parse("application/json"),
//                    json
//            );
//            System.out.println("Request Body: " + json);
//            Request request = new Request.Builder()
//                    .url("https://10.158.62.154:15443/api/v1/cipher/batch/encrypt")
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Authorization", "Basic " + token)
//                    .post(body)
//                    .build();
//            System.out.println("Request URL: " + request.url());
//            System.out.println("Request Headers:");
//            for (String headerName : request.headers().names()) {
//                System.out.println(headerName + ": " + request.header(headerName));
//            }
//            String strResponse = null;
//            try (Response response = client.newCall(request).execute()) {
//                if (response.isSuccessful() && response.body() != null) {
//                    strResponse = response.body().string();
//                    System.out.println("Response: " + strResponse);
//                    try {
//                        JSONArray jsonArray = new JSONObject(strResponse).getJSONObject("data").getJSONArray("encData");
//                        for (int m = 0; m < jsonArray.length(); m++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(m);
//                            Iterator keys = jsonObject.keys();
//                            List<String> fields = new ArrayList<>();
//                            List<Object> values = new ArrayList<>();
//                            while (keys.hasNext()) {
//                                String key = (String) keys.next();
//                                String value = jsonObject.getString(key);
//                                fields.add(key);
//                                values.add(value);
//                            }
//                            StringBuilder sqlBuilder = new StringBuilder(String.format("INSERT INTO %s (", tableDB));
//                            StringBuilder placeholders = new StringBuilder("VALUES (");
//                            for (int i = 0; i < fields.size(); i++) {
//                                if (i > 0) {
//                                    sqlBuilder.append(", ");
//                                    placeholders.append(", ");
//                                }
//                                sqlBuilder.append(fields.get(i));
//                                placeholders.append("?");
//                            }
//
//                            sqlBuilder.append(") ").append(placeholders).append(")");
//                            String sql = sqlBuilder.toString();
//                            Connection connection = jdbcUtil.openConnection();
//                            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                                for (int i = 0; i < values.size(); i++) {
//                                    preparedStatement.setObject(i + 1, values.get(i));
//                                }
//                                preparedStatement.addBatch();
//                                preparedStatement.executeBatch();
//                            }
//                        }
//                        System.out.println("数据入库成功!");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        jdbcUtil.close();
//                    }
//                } else {
//                    System.out.println("Request failed with code: " + response.code());
//                    if (response.body() != null) {
//                        System.out.println("Response: " + strResponse);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void createSymmetryKeyHasCLOB(List<HashMap<String, Object>> encryptList, String userDB, String pwdDB, String urlDB, String driverDB, String tableDB) {
//        createSymmetryKeyHasCLOB("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", encryptList, null, userDB, pwdDB, urlDB, driverDB, tableDB);
//    }
//
//
////    //todo 处理虚谷数据库中字段值是clob大文本数据
////    public static void createSymmetryKeyHasCLOB(String token, HashMap<String, Object> hashMap) {
////        try {
////            final TrustManager[] trustAllCerts = new TrustManager[]{
////                    new X509TrustManager() {
////                        @Override
////                        public X509Certificate[] getAcceptedIssuers() {
////                            return new X509Certificate[0];
////                        }
////
////                        @Override
////                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
////                        }
////
////                        @Override
////                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
////                        }
////                    }
////            };
////
////            final SSLContext sslContext = SSLContext.getInstance("TLS");
////            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
////            final HostnameVerifier allHostsValid = (hostname, session) -> true;
////
////            OkHttpClient.Builder builder = new OkHttpClient.Builder();
////            Method method = builder.getClass().getMethod("sslSocketFactory", SSLSocketFactory.class, X509TrustManager.class);
////            method.invoke(builder, sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
////
////            OkHttpClient client = builder.hostnameVerifier(allHostsValid).build();
////
////            Set<String> strings = hashMap.keySet();
////            StringBuilder sb = new StringBuilder();
////            for (String key : strings) {
////                if (!key.equalsIgnoreCase("id")) {
////                    if (hashMap.get(key) == null || hashMap.get(key).equals("")){
////                        continue;
////                    }
////                    if (sb.length() == 0) {
////                        sb.append(String.format("\"%s\":\"%s\"", key, encodeToBase64(hashMap.get(key).toString())));
////                    } else {
////                        sb.append(",").append(String.format("\"%s\":\"%s\"", key, encodeToBase64(hashMap.get(key).toString())));
////                    }
////                }
////            }
////            String json = String.format("{\"keyCode\": \"%s\", \"algorithmParam\": \"%s\", \"data\": [{%s}]}",
////                    "70592de7d3064c3f86cabd8244082832", "SM4/ECB/PKCS7Padding", sb.toString().replaceAll("\"\\{", "{").replaceAll("}\"", "}"));
////            RequestBody body = RequestBody.create(
////                    MediaType.parse("application/json"),
////                    json
////            );
////
////            System.out.println("Request Body: " + json);
////            Request request = new Request.Builder()
////                    .url("https://10.158.62.154:15443/api/v1/cipher/batch/encrypt")
////                    .addHeader("Content-Type", "application/json")
////                    .addHeader("Authorization", "Basic " + token)
////                    .post(body)
////                    .build();
////
////            System.out.println("Request URL: " + request.url());
////            System.out.println("Request Headers:");
////            for (String headerName : request.headers().names()) {
////                System.out.println(headerName + ": " + request.header(headerName));
////            }
////
////            String strResponse = null;
////            try (Response response = client.newCall(request).execute()) {
////                if (response.isSuccessful() && response.body() != null) {
////                    strResponse = response.body().string();
////                    System.out.println("Response: " + strResponse);
////                    try {
////                        JSONObject jsonObject = new JSONObject(strResponse)
////                                .getJSONObject("data")
////                                .getJSONArray("encData").getJSONObject(0);
////                        Iterator<String> keys = jsonObject.keys();
////                        List<String> fields = new ArrayList<>();
////                        List<Object> values = new ArrayList<>();
////                        while (keys.hasNext()) {
////                            String key = keys.next();
////                            String value = jsonObject.getString(key);
////                            fields.add(key);
////                            values.add(value);
////                        }
////                        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO T_DATA_MATERIAL_ENCRY (");
////                        StringBuilder placeholders = new StringBuilder("VALUES (");
////                        for (int i = 0; i < fields.size(); i++) {
////                            if (i > 0) {
////                                sqlBuilder.append(", ");
////                                placeholders.append(", ");
////                            }
////                            sqlBuilder.append(fields.get(i));
////                            placeholders.append("?");
////                        }
////
////                        sqlBuilder.append(") ").append(placeholders).append(")");
////                        String sql = sqlBuilder.toString();
////                        Connection connection = JdbcUtil.openConnection();
////                        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
////                            for (int i = 0; i < values.size(); i++) {
////                                preparedStatement.setObject(i + 1, values.get(i));
////                            }
////                            preparedStatement.addBatch();
////                            preparedStatement.executeBatch();
////                        }
////                        System.out.println("数据入库成功!");
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    } finally {
////                        JdbcUtil.close();
////                    }
////                } else {
////                    System.out.println("Request failed with code: " + response.code());
////                    if (response.body() != null) {
////                        System.out.println("Response: " + strResponse);
////                    }
////                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
////
////    //todo 批量加密
////    public static void createSymmetryKeyBatch(String token, HashMap<String, Object> hashMap) {
////        try {
////            final TrustManager[] trustAllCerts = new TrustManager[]{
////                    new X509TrustManager() {
////                        @Override
////                        public X509Certificate[] getAcceptedIssuers() {
////                            return new X509Certificate[0];
////                        }
////
////                        @Override
////                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
////                        }
////
////                        @Override
////                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
////                        }
////                    }
////            };
////
////            final SSLContext sslContext = SSLContext.getInstance("TLS");
////            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
////            final HostnameVerifier allHostsValid = (hostname, session) -> true;
////
////            OkHttpClient.Builder builder = new OkHttpClient.Builder();
////            Method method = builder.getClass().getMethod("sslSocketFactory", SSLSocketFactory.class, X509TrustManager.class);
////            method.invoke(builder, sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
////
////            OkHttpClient client = builder.hostnameVerifier(allHostsValid).build();
////
////            Set<String> strings = hashMap.keySet();
////            StringBuilder sb = new StringBuilder();
////            for (String key : strings) {
////                if (!key.equalsIgnoreCase("id")) {
////                    if (key.equalsIgnoreCase("userinfo")) {
////                        continue;
////                    }
////                    if (sb.length() == 0) {
////                        sb.append(String.format("\"%s\":\"%s\"", key, encodeToBase64(hashMap.get(key).toString())));
////                    } else {
////                        sb.append(",").append(String.format("\"%s\":\"%s\"", key, encodeToBase64(hashMap.get(key).toString())));
////                    }
////                }
////            }
////            String json = String.format("{\"keyCode\": \"%s\", \"algorithmParam\": \"%s\", \"data\": [{%s}]}",
////                    "70592de7d3064c3f86cabd8244082832", "SM4/ECB/PKCS7Padding", sb.toString().replaceAll("\"\\{", "{").replaceAll("}\"", "}"));
////            RequestBody body = RequestBody.create(
////                    MediaType.parse("application/json"),
////                    json
////            );
////
////            System.out.println("Request Body: " + json);
////            Request request = new Request.Builder()
////                    .url("https://10.158.62.154:15443/api/v1/cipher/batch/encrypt")
////                    .addHeader("Content-Type", "application/json")
////                    .addHeader("Authorization", "Basic " + token)
////                    .post(body)
////                    .build();
////
////            System.out.println("Request URL: " + request.url());
////            System.out.println("Request Headers:");
////            for (String headerName : request.headers().names()) {
////                System.out.println(headerName + ": " + request.header(headerName));
////            }
////
////            String strResponse = null;
////            try (Response response = client.newCall(request).execute()) {
////                if (response.isSuccessful() && response.body() != null) {
////                    strResponse = response.body().string();
////                    System.out.println("Response: " + strResponse);
////                    try {
////                        JSONObject jsonObject = new JSONObject(strResponse)
////                                .getJSONObject("data")
////                                .getJSONArray("encData").getJSONObject(0);
////                        Iterator<String> keys = jsonObject.keys();
////                        List<String> fields = new ArrayList<>();
////                        List<Object> values = new ArrayList<>();
////                        while (keys.hasNext()) {
////                            String key = keys.next();
////                            String value = jsonObject.getString(key);
////                            fields.add(key);
////                            values.add(value);
////                        }
////                        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO t_sea_gis_polygon_encry (");
////                        StringBuilder placeholders = new StringBuilder("VALUES (");
////                        for (int i = 0; i < fields.size(); i++) {
////                            if (i > 0) {
////                                sqlBuilder.append(", ");
////                                placeholders.append(", ");
////                            }
////                            sqlBuilder.append(fields.get(i));
////                            placeholders.append("?");
////                        }
////
////                        sqlBuilder.append(") ").append(placeholders).append(")");
////                        String sql = sqlBuilder.toString();
////                        Connection connection = JdbcUtil.openConnection();
////                        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
////                            for (int i = 0; i < values.size(); i++) {
////                                preparedStatement.setObject(i + 1, values.get(i));
////                            }
////                            preparedStatement.addBatch();
////                            preparedStatement.executeBatch();
////                        }
////                        System.out.println("数据入库成功!");
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    } finally {
////                        JdbcUtil.close();
////                    }
////                } else {
////                    System.out.println("Request failed with code: " + response.code());
////                    if (response.body() != null) {
////                        System.out.println("Response: " + strResponse);
////                    }
////                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//
////    //todo 解密处理虚谷数据库中字段值是clob大文本数据
////    public static void decryptSymmetryKeyHasCLOB(String token, HashMap<String, Object> hashMap) {
////        try {
////            final TrustManager[] trustAllCerts = new TrustManager[]{
////                    new X509TrustManager() {
////                        @Override
////                        public X509Certificate[] getAcceptedIssuers() {
////                            return new X509Certificate[0];
////                        }
////
////                        @Override
////                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
////                        }
////
////                        @Override
////                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
////                        }
////                    }
////            };
////
////            final SSLContext sslContext = SSLContext.getInstance("TLS");
////            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
////            final HostnameVerifier allHostsValid = (hostname, session) -> true;
////
////            OkHttpClient.Builder builder = new OkHttpClient.Builder();
////            Method method = builder.getClass().getMethod("sslSocketFactory", SSLSocketFactory.class, X509TrustManager.class);
////            method.invoke(builder, sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
////
////            OkHttpClient client = builder.hostnameVerifier(allHostsValid).build();
////
////            Set<String> strings = hashMap.keySet();
////            StringBuilder sb = new StringBuilder();
////            for (String key : strings) {
////                if (!key.equalsIgnoreCase("id")) {
////                    if (key.equalsIgnoreCase("userinfo")) {
////                        continue;
////                    }
////                    if (sb.length() == 0) {
//////                        sb.append(String.format("\"%s\":\"%s\"",key,encodeToBase64(hashMap.get(key).toString())));
////                        sb.append(String.format("\"%s\":\"%s\"", key, hashMap.get(key).toString()));
////                    } else {
////                        sb.append(",").append(String.format("\"%s\":\"%s\"", key, hashMap.get(key).toString()));
////                    }
////                }
////            }
////            String json = String.format("{\"keyCode\": \"%s\", \"algorithmParam\": \"%s\", \"encData\": {%s}}",
////                    "70592de7d3064c3f86cabd8244082832", "SM4/ECB/PKCS7Padding", sb.toString().replaceAll("\"\\{", "{").replaceAll("}\"", "}"));
////            RequestBody body = RequestBody.create(
////                    MediaType.parse("application/json"),
////                    json
////            );
////
////            System.out.println("Request Body: " + json);
////            Request request = new Request.Builder()
////                    .url("https://10.158.62.154:15443/api/v1/cipher/batch/encrypt")
////                    .addHeader("Content-Type", "application/json")
////                    .addHeader("Authorization", "Basic " + token)
////                    .post(body)
////                    .build();
////
////            System.out.println("Request URL: " + request.url());
////            System.out.println("Request Headers:");
////            for (String headerName : request.headers().names()) {
////                System.out.println(headerName + ": " + request.header(headerName));
////            }
////
////            String strResponse = null;
////            try (Response response = client.newCall(request).execute()) {
////                if (response.isSuccessful() && response.body() != null) {
////                    strResponse = response.body().string();
////                    System.out.println("Response: " + strResponse);
////                    try {
////                        JSONObject jsonObject = new JSONObject(strResponse).getJSONArray("encData").getJSONObject(0);
////                        Iterator keys = jsonObject.keys();
////                        while (keys.hasNext()) {
////                            System.out.println(keys.next());
////                            System.out.println(decodeFromBase64(jsonObject.getString(keys.next().toString())));
////                        }
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                } else {
////                    System.out.println("Request failed with code: " + response.code());
////                    if (response.body() != null) {
////                        System.out.println("Response: " + strResponse);
////                    }
////                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//
////    @Test
////    void decryptHasCLOB() {
////        List<HashMap<String, Object>> list = testMapper.selectEncryStationInfo();
////        ArrayList<String> list1 = new ArrayList<>();
////        list1.add("id");
//////        ArrayList<HashMap<String, Object>> list2 = decryptSymmetryKeyHasCLOB("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", list, list1);
////        ArrayList<HashMap<String, Object>> list2 = App.decryptBath("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", list, list1);
////        System.out.println(list2);
////
////    }
//
//    //todo 解密处理虚谷数据库中字段值是clob大文本数据
//    public static ArrayList<HashMap<String, Object>> decryptSymmetryKeyHasCLOB(String token, List<HashMap<String, Object>> encryptList, List<String> excludeParamsList) {
//        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
//        try {
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return new X509Certificate[0];
//                        }
//
//                        @Override
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        }
//                    }
//            };
//
//            final SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            final HostnameVerifier allHostsValid = (hostname, session) -> true;
//
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            Method method = builder.getClass().getMethod("sslSocketFactory", SSLSocketFactory.class, X509TrustManager.class);
//            method.invoke(builder, sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
//
//            OkHttpClient client = builder.hostnameVerifier(allHostsValid).build();
//
//
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < encryptList.size(); i++) {
//                HashMap<String, Object> hashMap = encryptList.get(i);
//                Set<String> keySet = hashMap.keySet();
//                if (i == 0) {
//                    sb.append("{");
//                } else {
//                    sb.append(",{");
//                }
//                for (String key : keySet) {
//                    if (excludeParamsList != null && !excludeParamsList.contains(key)) {
//                        if (hashMap.get(key) == null || hashMap.get(key).equals("")) {
//                            continue;
//                        }
//                        sb.append(String.format("\"%s\":\"%s\",", key, hashMap.get(key).toString()));
//                    }
//                }
//                sb.append("}");
//            }
//            String json = String.format("{\"keyCode\": \"%s\", \"algorithmParam\": \"%s\", \"encData\": [%s],\"iv\":\"\"}",
//                    "70592de7d3064c3f86cabd8244082832", "SM4/ECB/PKCS7Padding", sb.toString().replaceAll("\"\\{", "{").replaceAll("}\"", "}").replaceAll(",}", "}"));
//            RequestBody body = RequestBody.create(
//                    MediaType.parse("application/json"),
//                    json
//            );
//
//            System.out.println("Request Body: " + json);
//            Request request = new Request.Builder()
//                    .url("https://10.158.62.154:15443/api/v1/cipher/batch/decrypt")
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Authorization", "Basic " + token)
//                    .post(body)
//                    .build();
//
//            System.out.println("Request URL: " + request.url());
//            System.out.println("Request Headers:");
//            for (String headerName : request.headers().names()) {
//                System.out.println(headerName + ": " + request.header(headerName));
//            }
//            String strResponse = null;
//            Response response = client.newCall(request).execute();
//            if (response.isSuccessful() && response.body() != null) {
//                strResponse = response.body().string();
//                System.out.println("Response: " + strResponse);
//                JSONArray jsonArray = new JSONObject(strResponse).getJSONArray("data");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    HashMap<String, Object> hashMap = new HashMap<>();
//                    Iterator keys = jsonObject.keys();
//                    while (keys.hasNext()) {
//                        String key = keys.next().toString();
//                        hashMap.put(key, decodeFromBase64(jsonObject.getString(key)));
//                    }
//                    list.add(hashMap);
//                }
//            } else {
//                System.out.println("Request failed with code: " + response.code());
//                if (response.body() != null) {
//                    System.out.println("Response: " + strResponse);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//
//    //todo 解密
//    public static void decryptSymmetryKey(String token, HashMap<String, Object> hashMap) {
//        try {
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return new X509Certificate[0];
//                        }
//
//                        @Override
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        }
//                    }
//            };
//
//            final SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            final HostnameVerifier allHostsValid = (hostname, session) -> true;
//
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            Method method = builder.getClass().getMethod("sslSocketFactory", SSLSocketFactory.class, X509TrustManager.class);
//            method.invoke(builder, sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
//
//            OkHttpClient client = builder.hostnameVerifier(allHostsValid).build();
//
//            Set<String> strings = hashMap.keySet();
//            StringBuilder sb = new StringBuilder();
//            for (String key : strings) {
//                if (!key.equalsIgnoreCase("id")) {
//                    if (key.equalsIgnoreCase("userinfo")) {
//                        continue;
//                    }
//                    if (sb.length() == 0) {
////                        sb.append(String.format("\"%s\":\"%s\"",key,encodeToBase64(hashMap.get(key).toString())));
//                        sb.append(String.format("\"%s\":\"%s\"", key, hashMap.get(key).toString()));
//                    } else {
//                        sb.append(",").append(String.format("\"%s\":\"%s\"", key, hashMap.get(key).toString()));
//                    }
//                }
//            }
//            String json = String.format("{\"keyCode\": \"%s\", \"algorithmParam\": \"%s\", \"encData\": {%s}}",
//                    "9e724631d0de4ac0805f796a23271e07", "SM2", sb.toString().replaceAll("\"\\{", "{").replaceAll("}\"", "}"));
//            RequestBody body = RequestBody.create(
//                    MediaType.parse("application/json"),
//                    json
//            );
//
//            System.out.println("Request Body: " + json);
//            Request request = new Request.Builder()
//                    .url("https://10.158.62.154:15443/api/v1/cipher/json/decrypt")
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Authorization", "Basic " + token)
//                    .post(body)
//                    .build();
//
//            System.out.println("Request URL: " + request.url());
//            System.out.println("Request Headers:");
//            for (String headerName : request.headers().names()) {
//                System.out.println(headerName + ": " + request.header(headerName));
//            }
//
//            String strResponse = null;
//            try (Response response = client.newCall(request).execute()) {
//                if (response.isSuccessful() && response.body() != null) {
//                    strResponse = response.body().string();
//                    System.out.println("Response: " + strResponse);
//                    try {
//                        JSONObject jsonObject = new JSONObject(strResponse).getJSONObject("data");
//                        Iterator keys = jsonObject.keys();
//                        while (keys.hasNext()) {
//                            System.out.println(keys.next());
//                            System.out.println(decodeFromBase64(jsonObject.getString(keys.next().toString())));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    System.out.println("Request failed with code: " + response.code());
//                    if (response.body() != null) {
//                        System.out.println("Response: " + strResponse);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    //todo 批量解密
//    public static void decryptSymmetryKeyBatch(String token, HashMap<String, Object> hashMap) {
//        try {
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return new X509Certificate[0];
//                        }
//
//                        @Override
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        }
//                    }
//            };
//
//            final SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            final HostnameVerifier allHostsValid = (hostname, session) -> true;
//
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            Method method = builder.getClass().getMethod("sslSocketFactory", SSLSocketFactory.class, X509TrustManager.class);
//            method.invoke(builder, sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
//
//            OkHttpClient client = builder.hostnameVerifier(allHostsValid).build();
//
//            Set<String> strings = hashMap.keySet();
//            StringBuilder sb = new StringBuilder();
//            for (String key : strings) {
//                if (!key.equalsIgnoreCase("id")) {
//                    if (key.equalsIgnoreCase("userinfo")) {
//                        continue;
//                    }
//                    if (sb.length() == 0) {
////                        sb.append(String.format("\"%s\":\"%s\"",key,encodeToBase64(hashMap.get(key).toString())));
//                        sb.append(String.format("\"%s\":\"%s\"", key, hashMap.get(key).toString()));
//                    } else {
//                        sb.append(",").append(String.format("\"%s\":\"%s\"", key, hashMap.get(key).toString()));
//                    }
//                }
//            }
//            String json = String.format("{\"keyCode\": \"%s\", \"algorithmParam\": \"%s\", \"encData\": {%s}}",
//                    "9e724631d0de4ac0805f796a23271e07", "SM2", sb.toString().replaceAll("\"\\{", "{").replaceAll("}\"", "}"));
//            RequestBody body = RequestBody.create(
//                    MediaType.parse("application/json"),
//                    json
//            );
//
//            System.out.println("Request Body: " + json);
//            Request request = new Request.Builder()
//                    .url("https://10.158.62.154:15443/api/v1/cipher/json/decrypt")
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Authorization", "Basic " + token)
//                    .post(body)
//                    .build();
//
//            System.out.println("Request URL: " + request.url());
//            System.out.println("Request Headers:");
//            for (String headerName : request.headers().names()) {
//                System.out.println(headerName + ": " + request.header(headerName));
//            }
//
//            String strResponse = null;
//            try (Response response = client.newCall(request).execute()) {
//                if (response.isSuccessful() && response.body() != null) {
//                    strResponse = response.body().string();
//                    System.out.println("Response: " + strResponse);
//                    try {
//                        JSONObject jsonObject = new JSONObject(strResponse).getJSONArray("encData").getJSONObject(0);
//                        Iterator keys = jsonObject.keys();
//                        while (keys.hasNext()) {
//                            System.out.println(keys.next());
//                            System.out.println(decodeFromBase64(jsonObject.getString(keys.next().toString())));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    System.out.println("Request failed with code: " + response.code());
//                    if (response.body() != null) {
//                        System.out.println("Response: " + strResponse);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Test
//    void createMAC() {
//        String tableName = "T_STATION_ALL_ENCRY";
//        List<HashMap<String, Object>> list = testMapper.selectStaInfoData();
//        for (int i = 0; i < list.size(); i++) {
//            HashMap<String, Object> hashMap = list.get(i);
//            StringBuilder sb = new StringBuilder();
//            Set<String> strings = hashMap.keySet();
//            for (String str : strings) {
//                sb.append(hashMap.get(str));
//            }
//            String[] result = createMAC("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", sb.toString());
//            System.out.println("result");
//            System.out.println(Arrays.asList(result));
//            String sql = String.format("update %s set mac = '%s', iv = '%s' where id = '%s'", tableName, result[0], result[1], hashMap.get("id").toString());
////            String sql = String.format("update %s set mac = '%s', iv = '%s' where id = '%s'", tableName, result[0], result[1], hashMap.get("ID").toString());
////            String sql = String.format("update %s set mac = '%s' where StationNum = '%s'", tableName, mac, hashMap.get("StationNum").toString());
//            System.out.println(sql);
////            JdbcUtil jdbcUtil = new JdbcUtil("SYSDBA", "Sysdba2023@Xugu", "jdbc:xugu://10.159.90.201:31239/GRIDFSTDB", "com.xugu.cloudjdbc.Driver");
//            JdbcUtil jdbcUtil = new JdbcUtil("SYSDBA", "Sysdba2023@Xugu", "jdbc:xugu://10.159.90.202:36034/system", "com.xugu.cloudjdbc.Driver");
////            JdbcUtil jdbcUtil = new JdbcUtil("SYSDBA", "Sysdba2024@Xugu", "jdbc:xugu://10.159.90.202:36034/ythpt", "com.xugu.cloudjdbc.Driver");
//            Connection connection = jdbcUtil.openConnection();
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                preparedStatement.executeUpdate();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//
//
////            HashMap<String, Object> hashMap = list.get(i);
////            StringBuilder sb = new StringBuilder();
////            Set<String> strings = hashMap.keySet();
////            for (String str : strings) {
////                sb.append(hashMap.get(str));
////            }
////
////                        String sql = String.format("update %s set mac = '%s' where id = %d", tableName, mac, Integer.parseInt(hashMap.get("id").toString()));
////            String sql = String.format("update %s set mac = '%s' where id = '%s'", tableName, mac, hashMap.get("id").toString());
//////            String sql = String.format("update %s set mac = '%s' where StationNum = '%s'", tableName, mac, hashMap.get("StationNum").toString());
//////            JdbcUtil jdbcUtil = new JdbcUtil("SYSDBA", "Sysdba2023@Xugu", "jdbc:xugu://10.159.90.201:31239/GRIDFSTDB", "com.xugu.cloudjdbc.Driver");
//////            JdbcUtil jdbcUtil = new JdbcUtil("SYSDBA", "Sysdba2023@Xugu", "jdbc:xugu://10.159.90.202:36034/system", "com.xugu.cloudjdbc.Driver");
////            JdbcUtil jdbcUtil = new JdbcUtil("SYSDBA", "Sysdba2024@Xugu", "jdbc:xugu://10.159.90.202:36034/ythpt", "com.xugu.cloudjdbc.Driver");
////            Connection connection = jdbcUtil.openConnection();
////            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
////                preparedStatement.execute();
////            } catch (SQLException e) {
////                throw new RuntimeException(e);
////            }
//
////
////        String mac = createMAC("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", "0/Qgy3bSRWedcumWqfdonA==1sfCcZTA+5WuMIaL6n9adAo7nf/UWEnyPfzRCx8kfj0j2L6uyxePj");
////        System.out.println("mac");
////        System.out.println(mac);
//
//    }
//
//    //todo 计算mac值
//    public static String[] createMAC(String token, String content) {
//        String[] result = new String[2];
//        try {
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return new X509Certificate[0];
//                        }
//
//                        @Override
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        }
//                    }
//            };
//
//            final SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            final HostnameVerifier allHostsValid = (hostname, session) -> true;
//
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            Method method = builder.getClass().getMethod("sslSocketFactory", SSLSocketFactory.class, X509TrustManager.class);
//            method.invoke(builder, sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
//
//            OkHttpClient client = builder.hostnameVerifier(allHostsValid).build();
//
//
//            String json = String.format("{\"keyCode\": \"%s\", \"algorithmParam\": \"%s\", \"data\": \"%s\"}",
//                    "70592de7d3064c3f86cabd8244082832", "SM4_MAC", encodeToBase64(content));
//            RequestBody body = RequestBody.create(
//                    MediaType.parse("application/json"),
//                    json
//            );
//
//            System.out.println("Request Body: " + json);
//            Request request = new Request.Builder()
//                    .url("https://10.158.62.154:15443/api/v1/cipher/mac")
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Authorization", "Basic " + token)
//                    .post(body)
//                    .build();
//
//            System.out.println("Request URL: " + request.url());
//            System.out.println("Request Headers:");
//            for (String headerName : request.headers().names()) {
//                System.out.println(headerName + ": " + request.header(headerName));
//            }
//
//            String strResponse = null;
//            try (Response response = client.newCall(request).execute()) {
//                if (response.isSuccessful() && response.body() != null) {
//                    strResponse = response.body().string();
//                    System.out.println("Response: " + strResponse);
//                    try {
//                        String mac = new JSONObject(strResponse).getJSONObject("data").getString("mac");
//                        result[0] = mac;
//                        result[1] = new JSONObject(strResponse).getJSONObject("data").getString("iv");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    System.out.println("Request failed with code: " + response.code());
//                    if (response.body() != null) {
//                        System.out.println("Response: " + strResponse);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//
//    @Test
//    void macVerify() {
//        macVerify("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", "MTIzNDU2", "IreJWHgTjYJ3VyexxRO7BQ==", "0+3/mCx3l3gnCtjs2pA10g==");
//    }
//
//    //todo 验证MAC
//    public static String macVerify(String token, String data, String mac, String iv) {
//        String strResponse = null;
//        try {
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return new X509Certificate[0];
//                        }
//
//                        @Override
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        }
//                    }
//            };
//
//            final SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            final HostnameVerifier allHostsValid = (hostname, session) -> true;
//
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            Method method = builder.getClass().getMethod("sslSocketFactory", SSLSocketFactory.class, X509TrustManager.class);
//            method.invoke(builder, sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
//
//            OkHttpClient client = builder.hostnameVerifier(allHostsValid).build();
//
//
//            String json = String.format("{\"keyCode\": \"%s\", \"algorithmParam\": \"%s\", \"data\": \"%s\", \"mac\": \"%s\", \"iv\": \"%s\"}",
//                    "70592de7d3064c3f86cabd8244082832", "SM4_MAC", data, mac, iv);
//            RequestBody body = RequestBody.create(
//                    MediaType.parse("application/json"),
//                    json
//            );
//
//            System.out.println("Request Body: " + json);
//            Request request = new Request.Builder()
//                    .url("https://10.158.62.154:15443/api/v1/cipher/macVerify")
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Authorization", "Basic " + token)
//                    .post(body)
//                    .build();
//
//            System.out.println("Request URL: " + request.url());
//            System.out.println("Request Headers:");
//            for (String headerName : request.headers().names()) {
//                System.out.println(headerName + ": " + request.header(headerName));
//            }
//
//
//            try (Response response = client.newCall(request).execute()) {
//                if (response.isSuccessful() && response.body() != null) {
//                    strResponse = response.body().string();
//                    System.out.println("Response: " + strResponse);
//                    boolean aBoolean = new JSONObject(strResponse).getJSONObject("data").getBoolean("verifyResult");
//                    if (aBoolean) {
//                        System.out.println("数据未被篡改");
//                    } else {
//                        System.err.println("数据被篡改，请检查服务环境是否安全");
//                    }
//                } else {
//                    System.out.println("Request failed with code: " + response.code());
//                    if (response.body() != null) {
//                        System.out.println("Response: " + strResponse);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return strResponse;
//    }
//
//
//    /**
//     * 将字符串加密为 Base64 格式
//     */
//    public static String encodeToBase64(String data) {
//        return Base64.getEncoder().encodeToString(data.getBytes());
//    }
//
//    /**
//     * 将 Base64 字符串解密为原始字符串
//     */
//    public static String decodeFromBase64(String encodedData) {
//        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);
//        return new String(decodedBytes);
//    }
//
//    private byte[] secretKey;
//
//    @Test
//    public void testBCryptPasswordEncoder() {
//        String password = "Ythpt@2024";
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String encode = bCryptPasswordEncoder.encode(password);
//        System.out.println(encode);
//
//    }
//
//
//    @Autowired
//    private CustomRedisTemplate customRedisTemplate;
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
////    @Test
////    void testPassword(){
////        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
////        String password = bCryptPasswordEncoder.encode("Ythpt@2024");
////        System.out.println(password);
////    }
//
//
//    @Test
//    void testUserInfo() {
////        String accessToken = JwtUtil.createAccessToken("guangxi,23");
////        System.out.println("accessToken");
////        System.out.println(accessToken);
//        Claims claims = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1NTFhZmQ4Ny04YmI3LTQyZWEtYTEwOS1kNGJiNWVjMTRhNmMiLCJzdWIiOiJhZG1pbiwxIiwiaWF0IjoxNzQyMDI5MTQ4LCJleHAiOjE3NDI2MzM5NDh9.VU16r9DZwMB1Qo41-ew0X6cWwrUjg_IaUybKKELYzos");
//        String subject = claims.getSubject();
//        System.out.println("subject");
//        System.out.println(subject);
//    }
////    @Test
////    void  testLogin(){
////        User user = new User();
////        user.setUserName("admin");
////        user.setPassword("admin123");
////        Authentication authenticate = null;
////        LoginUser loginUser = null;
////        try {
////            if (user.getUserName().isEmpty() || user.getPassword().isEmpty()) {
////                System.out.printf("username or password is empty");
////            }
////            System.out.println("user: "+user);
////            User userNew = userMapper.getUserByUserName(user.getUserName());
////            HashMap<String, Object> map = userMapper.selectUserPermissions(userNew.getUserName());
////            List<String> list = new ArrayList<>(Arrays.asList(map.get("role").toString()));
////            loginUser = new LoginUser(userNew, list);
////        } catch (Exception e) {
////            e.printStackTrace();
////            System.out.printf("登陆失败");//防止空指针异常
////        }
////        //使用userid生成token
////        String userId = loginUser.getUser().getId().toString();
////        String jwt = JwtUtil.createAccessToken(userId);
////        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
////        authenticate = authenticationManager.authenticate(authenticationToken);
////        if (Objects.isNull(authenticate)) {
////            System.out.printf("用户名或密码错误");
////        }
////        //Redis存储信息
////        customRedisTemplate.opsForValue().set("user_" + loginUser.getUser().getUserName(), loginUser);
////        customRedisTemplate.opsForValue().set("token_" + loginUser.getUser().getUserName(), jwt);
//////        customRedisTemplate.opsForValue().set("refreshToken:" + userId, refreshToken);
////        //把token响应给前端
////        HashMap<String, String> map = new HashMap<>();
////        map.put("token", jwt);
////        map.put("userName", loginUser.getUser().getUserName());
////        System.out.printf("map: "+map);;
////    }
//
//
////    @Test
////    public void init() {
////        // 生成一个默认长度为 256 位（32 字节）的密钥
////        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
////        System.out.println(secretKey.toString());
////    }
////
////    public String createToken(String username) {
////        // 使用生成的密钥创建 JWT 令牌
////        String token = Jwts.builder()
////                .setSubject(username)
////                .signWith(SignatureAlgorithm.HS256, secretKey)
////                .compact();
////        return token;
////    }
//
//}
