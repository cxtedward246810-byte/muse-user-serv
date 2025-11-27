//package com.tao.userloginandauth;
//
//import com.tao.userloginandauth.mapper.TestMapper;
//import com.tao.util.JdbcUtil;
//import okhttp3.*;
//import org.codehaus.jettison.json.JSONArray;
//import org.codehaus.jettison.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.net.ssl.*;
//import java.lang.reflect.Method;
//import java.security.cert.X509Certificate;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.util.*;
//
///**
// * @Description TODO
// * @Author puxing
// * @Date 2025/4/28
// */
//@SpringBootTest
//public class DataTest {
//    @Autowired
//    private TestMapper testMapper;
//
//
//    @Test
//    void test() {
//        List<HashMap<String, Object>> list = testMapper.selectData();
////        List<HashMap<String, Object>> list = new ArrayList<>();
////        HashMap<String, Object> hashMap = new HashMap<>();
////        hashMap.put("key","")
////        List<HashMap<String, Object>> list = testMapper.selectStaInfoData();
////        List<HashMap<String, Object>> list = testMapper.selectGisPolygon();
//        ArrayList<String> list1 = new ArrayList<>();
//        list1.add("id");
//        encryBatch("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", list, list1, "SYSDBA", "Sysdba2024@Xugu", "jdbc:xugu://10.159.90.202:36034/ythpt", "com.xugu.cloudjdbc.Driver", "t_ythpt_user_encry");
////        encryBatch("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", list, list1, "SYSDBA", "Sysdba2024@Xugu", null, "com.xugu.cloudjdbc.Driver", "t_ythpt_user_encry");
//    }
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
//     * @description 批量加密
//     */
//    public static void encryBatch(String token, List<HashMap<String, Object>> encryptList, List<String> excludeParamsList, String userDB, String pwdDB, String urlDB, String driverDB, String tableDB) {
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
//    private static void encryBatch(List<HashMap<String, Object>> encryptList, String userDB, String pwdDB, String urlDB, String driverDB, String tableDB) {
//        encryBatch("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", encryptList, null, userDB, pwdDB, urlDB, driverDB, tableDB);
//    }
//
//    /**
//     * 将字符串加密为 Base64 格式
//     */
//    private static String encodeToBase64(String data) {
//        return Base64.getEncoder().encodeToString(data.getBytes());
//    }
//
//    /**
//     * 将 Base64 字符串解密为原始字符串
//     */
//    private static String decodeFromBase64(String encodedData) {
//        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);
//        return new String(decodedBytes);
//    }
//
//
//}
//
