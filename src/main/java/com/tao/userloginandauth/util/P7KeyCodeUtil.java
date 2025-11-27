//package com.tao.userloginandauth.util;
//
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//import javax.net.ssl.*;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.security.cert.X509Certificate;
//
///**
// * @Description TODO 数据加密
// * @Author puxing
// * @Date 2025/3/24
// */
//public class P7KeyCodeUtil {
//    public static void main(String[] args) {
//        createAsymmetricKey("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==");
//        createSymmetryKey("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==");
//    }
//
//
//    //todo 非对称（对数据库数据加密） 9e724631d0de4ac0805f796a23271e07
//    public static void createAsymmetricKey(String token) {
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
//            String json = String.format("{\"algorithm\": \"%s\", \"length\": \"%d\", \"keyUsages\": \"%s\", \"period\": \"%d\",\"keyNumber\":\"%s\",\"description\":\"%s\"}",
//                    "SM2", 256, "0,1,2,3", 365, 1, "创建非对称密匙");
//            RequestBody body = RequestBody.create(
//                    MediaType.parse("application/json"),
//                    json
//            );
//
//            System.out.println("Request Body: " + json);
//            Request request = new Request.Builder()
//                    .url("https://10.158.62.154:15443/api/v1/kms/dek/createKeyPair")
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
//            try (Response response = client.newCall(request).execute()) {
//                if (response.isSuccessful() && response.body() != null) {
//                    System.out.println("Response: " + response.body().string());
//                } else {
//                    System.out.println("Request failed with code: " + response.code());
//                    if (response.body() != null) {
//                        System.out.println("Response: " + response.body().string());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
////    public static void createSymmetryKey(String token) {
////        try {
////            // 创建一个信任所有证书的 TrustManager
////            final TrustManager[] trustAllCerts = new TrustManager[]{
////                    new X509TrustManager() {
////                        @Override
////                        public X509Certificate[] getAcceptedIssuers() {
////                            return new X509Certificate[0];
////                        }
////
////                        @Override
////                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
////
////                        @Override
////                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
////                    }
////            };
////
////            // 初始化 SSL 上下文并安装信任管理器
////            final SSLContext sslContext = SSLContext.getInstance("TLS");
////            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
////
////            // 创建一个不验证主机名的 HostnameVerifier
////            final HostnameVerifier allHostsValid = (hostname, session) -> true;
////
////            // 使用反射调用 OkHttp 的内部方法
////            OkHttpClient.Builder builder = new OkHttpClient.Builder();
////            Method method = builder.getClass().getMethod("sslSocketFactory", SSLSocketFactory.class, X509TrustManager.class);
////            method.invoke(builder, sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
////
////            // 设置 HostnameVerifier
////            OkHttpClient client = builder.hostnameVerifier(allHostsValid).build();
////
////            // 构造请求体
////            String json = String.format("{\"algorithm\": \"%s\", \"length\": \"%d\", \"keyUsages\": \"%s\", \"period\": \"%d\",\"keyNumber\":\"%s\",\"description\":\"%s\"}", "SM2", 256, "0,3", 100, 1, "创建非对称密匙");
////            RequestBody body = RequestBody.create(
////                    MediaType.parse("application/json"),
////                    json
////            );
////
////            // 构造请求
////            Request request = new Request.Builder()
////                    .url("https://10.158.62.154:15443/api/v1/kms/dek/createKeyPair")
////                    .addHeader("Content-Type", "application/json")
////                    .addHeader("Authorization", String.format("Bearer %s", token))
////                    .addHeader("Accept", "*/*")
////                    .addHeader("Accept-Encoding", "gzip, deflate, br")
////                    .addHeader("User-Agent", "PostmanRuntime-ApipostRuntime/1.1.0")
////                    .addHeader("Connection", "keep-alive")
////                    .post(body)
////                    .build();
////
////            // 执行请求
////            try (Response response = client.newCall(request).execute()) {
////                if (response.isSuccessful() && response.body() != null) {
////                    System.out.println("Response: " + response.body().string());
////                } else {
////                    System.out.println("Request failed with code: " + response.code());
////                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////    public static void createSymmetryKey(String token){//对称加密
////        OkHttpClient client = new OkHttpClient();
////        String json = String.format("{\"algorithm\": \"%s\", \"length\": \"%d\", \"keyUsages\": \"%d\", \"period\": \"%d\"}","SM1",128,0,365);
////        RequestBody body = RequestBody.create(
////                json,
////                MediaType.parse("application/json; charset=utf-8")
////        );
////        Request request = new Request.Builder()
////                .url("https://10.158.62.154:15443/api/v1/kms/dek/createKey")
////                .addHeader("Authorization", String.format("Bearer %s",token))
////                .post(body)
////                .build();
////        try (Response response = client.newCall(request).execute()) {
////            if (response.isSuccessful() && response.body() != null) {
////                System.out.println("Response: " + response.body().string());
////                response.body().string();
////            } else {
////                System.out.println("Request failed with code: " + response.code());
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
//
//
//    //    public static String createAsymmetricKey(String token){//非对称加密
////        OkHttpClient client = new OkHttpClient();
////        String json = String.format("{\"algorithm\": \"%s\", \"length\": \"%d\", \"keyUsages\": \"%d\", \"period\": \"%d\"}","SM1",128,0,365);
////        RequestBody body = RequestBody.create(
////                json,
////                MediaType.parse("application/json; charset=utf-8")
////        );
////        Request request = new Request.Builder()
////                .url("https://10.158.62.154:15443/api/v1/kms/dek/createKey")
////                .addHeader("Authorization", String.format("Bearer %s",token))
////                .post(body)
////                .build();
////        try (Response response = client.newCall(request).execute()) {
////            if (response.isSuccessful() && response.body() != null) {
////                System.out.println("Response: " + response.body().string());
////                return  response.body().string();
////            } else {
////                System.out.println("Request failed with code: " + response.code());
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        return null;
////    }
//    //todo 对称密匙（计算MAC，对需要传输的密码进行加密） 70592de7d3064c3f86cabd8244082832
//    public static void createSymmetryKey(String token) {
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
//            String json = String.format("{\"algorithm\": \"%s\", \"length\": \"%d\", \"keyUsages\": \"%s\", \"period\": \"%d\",\"keyNumber\":\"%s\",\"description\":\"%s\"}",
//                    "SM4", 128, "0,1,2", 365, 1, "创建对称密匙");
//            RequestBody body = RequestBody.create(
//                    MediaType.parse("application/json"),
//                    json
//            );
//
//            System.out.println("Request Body: " + json);
//            Request request = new Request.Builder()
//                    .url("https://10.158.62.154:15443/api/v1/kms/dek/createKey")
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
//            try (Response response = client.newCall(request).execute()) {
//                if (response.isSuccessful() && response.body() != null) {
//                    System.out.println("Response: " + response.body().string());
//                } else {
//                    System.out.println("Request failed with code: " + response.code());
//                    if (response.body() != null) {
//                        System.out.println("Response: " + response.body().string());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
//
