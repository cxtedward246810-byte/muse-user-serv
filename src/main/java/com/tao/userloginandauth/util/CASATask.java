package com.tao.userloginandauth.util;//package com.tao.util;


import com.tao.userloginandauth.mapper.CASAMapper;
import okhttp3.*;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.lang.reflect.Method;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @Description //TODO 密评任务
 * Create by 2023/6/6
 */

@Component
public class CASATask {

    @Autowired
    private CASAMapper casaMapper;

    @Scheduled(cron = "0 */5 * * * ?")//每5分钟执行
//    @Scheduled(cron = "0 * * * * ?")//每5分钟执行
    public void myTask() {
        List<HashMap<String, Object>> list = casaMapper.selectData();
        for (HashMap<String, Object> hashMap : list) {
            Set<String> keySet = hashMap.keySet();
            StringBuilder sb = new StringBuilder();
            for (String str : keySet) {
                if (!str.equals("mac") && !str.equals("iv")) {
                    sb.append(hashMap.get(str));
                }
            }
            macVerify("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", encodeToBase64(sb.toString()), hashMap.get("mac").toString(), hashMap.get("iv").toString());
        }
    }

    private static String encodeToBase64(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    //todo 验证MAC
    public static String macVerify(String token, String data, String mac, String iv) {
        String strResponse = null;
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final HostnameVerifier allHostsValid = (hostname, session) -> true;

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            Method method = builder.getClass().getMethod("sslSocketFactory", SSLSocketFactory.class, X509TrustManager.class);
            method.invoke(builder, sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);

            OkHttpClient client = builder.hostnameVerifier(allHostsValid).build();


            String json = String.format("{\"keyCode\": \"%s\", \"algorithmParam\": \"%s\", \"data\": \"%s\", \"mac\": \"%s\", \"iv\": \"%s\"}",
                    "70592de7d3064c3f86cabd8244082832", "SM4_MAC", data, mac, iv);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);

            System.out.println("Request Body: " + json);
            Request request = new Request.Builder()
                    .url("https://10.158.62.154:15443/api/v1/cipher/macVerify")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Basic " + token)
                    .post(body)
                    .build();
//            System.out.println("Request URL: " + request.url());
//            System.out.println("Request Headers:");
//            for (String headerName : request.headers().names()) {
//                System.out.println(headerName + ": " + request.header(headerName));
//            }
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    strResponse = response.body().string();
//                    System.out.println("Response: " + strResponse);
                    boolean aBoolean = new JSONObject(strResponse).getJSONObject("data").getBoolean("verifyResult");
                    if (aBoolean) {
                        System.out.println("数据未被篡改");
                    } else {
                        System.err.println("数据被篡改，请检查服务环境是否安全");
                    }
                } else {
                    System.out.println("Request failed with code: " + response.code());
                    if (response.body() != null) {
                        System.out.println("Response: " + strResponse);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }


}
