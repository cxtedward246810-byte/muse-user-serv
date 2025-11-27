//package com.tao.userloginandauth;
//
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.security.KeyFactory;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Description TODO
// * @Author puxing
// * @Date 2025/5/7
// */
//public class Test {
//    public static void main(String[] args) throws IOException {
////        String s = decodeFromBase64("Q0495qKB5a2Y5qGCLEU9MjYzMjkzMDExNkBxcS5jb20sTz3lub/opb/lo67ml4/oh6rmsrvljLrmsJTosaHlsYDkv6Hmga/kuK3lv4MsQz1DTg==");
////        System.out.println(s);
////        String s1 = decodeFromBase64("Q049QmVpamluZyBTTTIgQ0EsT1U9QkpDQSxPPUJKQ0EsQz1DTg==");
////        System.out.println(s1);
////        String s2 = decodeFromBase64("MIIDgDCCAyWgAwIBAgIKGhAAAAAABYs9szAKBggqgRzPVQGDdTBEMQswCQYDVQQGEwJDTjENMAsGA1UECgwEQkpDQTENMAsGA1UECwwEQkpDQTEXMBUGA1UEAwwOQmVpamluZyBTTTIgQ0EwHhcNMjUwMTE5MTYwMDAwWhcNMjYwMTIwMTU1OTU5WjB4MQswCQYDVQQGDAJDTjEzMDEGA1UECgwq5bm/6KW/5aOu5peP6Ieq5rK75Yy65rCU6LGh5bGA5L+h5oGv5Lit5b+DMSAwHgYJKoZIhvcNAQkBDBEyNjMyOTMwMTE2QHFxLmNvbTESMBAGA1UEAwwJ5qKB5a2Y5qGCMFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEucQ9F1yqRcKdum2kx+rCsgMDYS/PK7iw9tMnDcQmX96xBRKfOHPqIzfOQzul+9tGWVjNP4G9m7gAXp3EZaypIqOCAckwggHFMB8GA1UdIwQYMBaAFB/mz9SPxSIql0opihXnFsmSNMS2MB0GA1UdDgQWBBQxuqbSq5nQfsFyBC2LymtEkQbV2DAOBgNVHQ8BAf8EBAMCBsAwgZ8GA1UdHwSBlzCBlDBhoF+gXaRbMFkxCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMQ0wCwYDVQQLDARCSkNBMRcwFQYDVQQDDA5CZWlqaW5nIFNNMiBDQTETMBEGA1UEAxMKY2EyMWNybDQ3MTAvoC2gK4YpaHR0cDovL2NybC5iamNhLm9yZy5jbi9jcmwvY2EyMWNybDQ3MS5jcmwwJAYKKoEchu8yAgEBAQQWDBRTRjQ1MDcyMTE5OTMwOTA1MzQ1NjAgBghghkgBhvhEAgQUDBI0NTA3MjExOTkzMDkwNTM0NTYwHwYKKoEchu8yAgEBDgQRDA8xMDIwODAwNTMyMzY5OTMwLQYKKoEchu8yAgEBFwQfDB0xQDIxNTAwOVNGMDQ1MDcyMTE5OTMwOTA1MzQ1NjAgBggqgRzQFAQBAQQUDBI0NTA3MjExOTkzMDkwNTM0NTYwFwYKKoEchu8yAgEBHgQJDAcxMDAwODUxMAoGCCqBHM9VAYN1A0kAMEYCIQC/Z9LpP4OLU+pNuKhuIdu0ltE324UsuAyGyvYrnz8CWwIhALixgG3Bxbsy/YsiGT7HfrKfil5LhvvLdOJOGfbKTv3V");
////        System.out.println(s2);
////        String cloudtao = App.detachverifysign("aWNzcGRlbW9fR1hRWFRfWUI6WXRocHRAMjAyNA==", "MIIEfgYKKoEcz1UGAQQCAqCCBG4wggRqAgEBMQwwCgYIKoEcz1UBgxEwDAYKKoEcz1UGAQQCAaCCA4QwggOAMIIDJaADAgECAgoaEAAAAAAFiz2zMAoGCCqBHM9VAYN1MEQxCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMQ0wCwYDVQQLDARCSkNBMRcwFQYDVQQDDA5CZWlqaW5nIFNNMiBDQTAeFw0yNTAxMTkxNjAwMDBaFw0yNjAxMjAxNTU5NTlaMHgxCzAJBgNVBAYMAkNOMTMwMQYDVQQKDCrlub/opb/lo67ml4/oh6rmsrvljLrmsJTosaHlsYDkv6Hmga/kuK3lv4MxIDAeBgkqhkiG9w0BCQEMETI2MzI5MzAxMTZAcXEuY29tMRIwEAYDVQQDDAnmooHlrZjmoYIwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAAS5xD0XXKpFwp26baTH6sKyAwNhL88ruLD20ycNxCZf3rEFEp84c+ojN85DO6X720ZZWM0/gb2buABencRlrKkio4IByTCCAcUwHwYDVR0jBBgwFoAUH+bP1I/FIiqXSimKFecWyZI0xLYwHQYDVR0OBBYEFDG6ptKrmdB+wXIELYvKa0SRBtXYMA4GA1UdDwEB/wQEAwIGwDCBnwYDVR0fBIGXMIGUMGGgX6BdpFswWTELMAkGA1UEBhMCQ04xDTALBgNVBAoMBEJKQ0ExDTALBgNVBAsMBEJKQ0ExFzAVBgNVBAMMDkJlaWppbmcgU00yIENBMRMwEQYDVQQDEwpjYTIxY3JsNDcxMC+gLaArhilodHRwOi8vY3JsLmJqY2Eub3JnLmNuL2NybC9jYTIxY3JsNDcxLmNybDAkBgoqgRyG7zICAQEBBBYMFFNGNDUwNzIxMTk5MzA5MDUzNDU2MCAGCGCGSAGG+EQCBBQMEjQ1MDcyMTE5OTMwOTA1MzQ1NjAfBgoqgRyG7zICAQEOBBEMDzEwMjA4MDA1MzIzNjk5MzAtBgoqgRyG7zICAQEXBB8MHTFAMjE1MDA5U0YwNDUwNzIxMTk5MzA5MDUzNDU2MCAGCCqBHNAUBAEBBBQMEjQ1MDcyMTE5OTMwOTA1MzQ1NjAXBgoqgRyG7zICAQEeBAkMBzEwMDA4NTEwCgYIKoEcz1UBg3UDSQAwRgIhAL9n0uk/g4tT6k24qG4h27SW0TfbhSy4DIbK9iufPwJbAiEAuLGAbcHFuzL9iyIZPsd+sp+KXkuG+8t04k4Z9spO/dUxgcAwgb0CAQEwUjBEMQswCQYDVQQGEwJDTjENMAsGA1UECgwEQkpDQTENMAsGA1UECwwEQkpDQTEXMBUGA1UEAwwOQmVpamluZyBTTTIgQ0ECChoQAAAAAAWLPbMwDAYIKoEcz1UBgxEFADANBgkqgRzPVQGCLQEFAARHMEUCIElqluTXKtpmMiY2oVHFLTyezZ09E5u5vq41/+N/m2irAiEAgKW/0VoysyYD/0b8AwLIoarPG2YDLkgFqdRmDNJp/xA=", "cloudtao");
////        System.out.println(encodeToBase64("$2a$10$TFFMXYZmNdviQ7TzIyLCWePSUNH2gwQKi4Jkn5CIu2Tf63PSAgpYS"));
//
////        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
////        String password = bCryptPasswordEncoder.encode("Qhzx@2025.");
////        System.out.println(password);
////
////
//        testyToken();
//    }
//    private static String encodeToBase64(String data) {
//        return Base64.getEncoder().encodeToString(data.getBytes());
//    }
//
//    private static String decodeFromBase64(String encodedData) {
//        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);
//        return new String(decodedBytes);
//    }
//
//
//        public static void testyToken() throws IOException {
//            byte[] bytes = Files.readAllBytes(Paths.get("D:\\test\\demo\\publicKey.pem"));
//            String string = new String(bytes, StandardCharsets.UTF_8);
//
////        Claims claims = JwtUtil.parseJWT("eyJhbGciOiJUzl1NilsInR5cCl6lkpXVCJ9.eyJ1c2VySWQiOi3NmI4MTNmMizNiBKLTOwOTGtODkxOSOzNmVIODJNYzC30DMiLCI1c2VyTmftZSl6lnpvdXihb2ppliwidG9rZW5JZCI6IiK2MZU4NGEzOGUOMZU2ZmJIMWMWOTNhMTA0ZDJhZGU5NGEXOTA3M212ZjBkOTYyMilslmlhdCI6MTc1NzY0ODg3MiwiZhwlioxNzU4MiUzNicyfO.MANGR7AxaPiHXY0WYTG60M32zEkvdaNDaxBD3JDrОA");
////        Claims claims = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI3NmI4MTNmMi0zNjBkLTQwOTgtODkxOS0zNmVlODJlYzc3ODMiLCJ1c2VyTmFtZSI6InpvdXlhb2ppIiwidG9rZW5JZCI6IjU4NmU3NzE3NWRlYWFkYzg4NDIzMTEyYWViNWJkNjFhNjI2M2M1NGQ0M2Y4MDIzNSIsImlhdCI6MTc1ODA4MjIwMywiZXhwIjoxNzU4Njg3MDAzfQ.VEpGKWQD8NUvH2cXH5-6b5JrGKJAsqYQPVWDE3P_z0Q");
////        Claims claims = verifyJwt("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI3NmI4MTNmMi0zNjBkLTQwOTgtODkxOS0zNmVlODJlYzc3ODMiLCJ1c2VyTmFtZSI6InpvdXlhb2ppIiwidG9rZW5JZCI6Ijc0MWU3NTAyOGE2MTgyZTBkYjg5ZDg1ODliMDQwYzcyZWYxYmUxNmQ4ZDg3ZTBlOCIsImlhdCI6MTc1ODE4MjI2OSwiZXhwIjoxNzU4Nzg3MDY5fQ.DkJLPPKGm9MITKuowNGzGLUQdaJc_3NO7PL7gInWhn-nAf25tJJgClPUxPfeyVY65rR6XkgGUgdAwXfovgPXh9nRlfdrMfRghnAO9MjV4WdvHkTZJfjhgpO6HEOxqO01dAt82x-Pb5lJQzuDsZGo7p6h_f1IYQzEJW31uzCctwkX7Xu77UK1TKvwZ8s200o0yXoQmQcGA8BBD0GYlys8mkVabW8dzqS7U5XruIZyP29GTsXHgCo87VaFinlMbP_RRS1xtgVrvDYIPU_2wll0G7NlmjD4UC_x_3eS2XAaEPKixxgXhCPLnWJQSUCAtDVyP0eCvDv93JB4_bnIsA2LUA",string);
//            Claims claims = verifyJwt("eyJhbGciOiJSUzI1NiJ9.eyJ0b2tlbklkIjotMTE0OTMyODU5NCwiYWRtaW5Db2RlIjoiNDUwMDAwIiwidXNlck5hbWUiOiJhZG1pbiIsInVzZXJSb2xlIjpbMSwxXSwiaWF0IjoxNzYxMDE5MTU2LCJleHAiOjE3NjEyNzgzNTZ9.mcbftsdCmkMVQHDFYCutzaTh53fhFg7QgRhNa5vBF7lLfB0k-R1VtgmdZfBx0OWLdMg_v15yk3BYbSaxHqxg1TNW5vD3gitFt1b5Ob30VtGNjNeo2GU8z90UgU_5hDe_mpejTbNDfju5DfKcFvkKobQI7hBVsPI1Dm8nDRLlHHukwYTzTc_CQWmDxGkWL0-epKqZb-2Cg53HrSP73lJtKjnnhnHZRMTLcLKIoutyH9nxPNaLzrqnh4aQgWzgV7-MffnumFZ58aW9Msk6GHW9RkUWhfopZxVE206xxTapqMxr5kA24FKoqYHp84-aUfFXPlLCbFJu1urvxLlhcD2htQ",string);
////        Claims claims = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlMzExYWEyYS1iMTE1LTQ3ZDAtOTYwMy1jYzE1MWIzZWFhYTAiLCJzdWIiOiJ3cWRqa3dsMTJlMmRkMUZRbGsxajJkYXNkYWRkbXpzYWRhYWQyem91eWFvamkiLCJpYXQiOjE3NTgxNTg4MjQsImV4cCI6MTc1ODc2MzYyNH0.TgLetngl_wWy-zw4DS9lhd7LGxiz1qDuB6hrw36ht5s");
//
//            System.out.println(claims);
//        }
//
//        public static PublicKey getPublicKeyFromPem(String pemPublicKey) {
//            try {
//                // 移除 PEM 文件中的头尾标记和换行符
//                String publicKeyPEM = pemPublicKey
//                        .replace("-----BEGIN PUBLIC KEY-----", "")
//                        .replace("-----END PUBLIC KEY-----", "")
//                        .replaceAll("\\s", "");
//                byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
//                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
//                return keyFactory.generatePublic(keySpec);
//            } catch (Exception e) {
//                throw new RuntimeException("Failed to load public key", e);
//            }
//        }
//
//        public static Claims verifyJwt(String token, String publicKeyPem) {
//            try {
//                PublicKey publicKey = getPublicKeyFromPem(publicKeyPem);
//
//                return Jwts.parserBuilder()
//                        .setSigningKey(publicKey)
//                        .build()
//                        .parseClaimsJws(token)
//                        .getBody();
//            } catch (Exception e) {
//                throw new RuntimeException("JWT verification failed", e);
//            }
//        }
//
//
//        // ✅ 从 PEM 格式私钥字符串加载 PrivateKey
//        public static PrivateKey getPrivateKeyFromPem(String pemPrivateKey) {
//            try {
//                // 移除头尾标记和所有空白字符
//                String privateKeyPEM = pemPrivateKey
//                        .replace("-----BEGIN PRIVATE KEY-----", "")
//                        .replace("-----END PRIVATE KEY-----", "")
//                        .replaceAll("\\s", "");
//
//                byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
//
//                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
//                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//                return keyFactory.generatePrivate(keySpec);
//            } catch (Exception e) {
//                throw new RuntimeException("Failed to load private key", e);
//            }
//        }
//
//
//        // ✅ 使用私钥生成 JWT Token
//        public static String generateJwt(String privateKeyPem, Map<String, ?> map, long expirationMs) {
//            try {
//                PrivateKey privateKey = getPrivateKeyFromPem(privateKeyPem);
//
//                // 设置过期时间
//                Date now = new Date();
//                Date expiryDate = new Date(now.getTime() + expirationMs);
//                // 创建 JWT
//                return Jwts.builder()
//                        .setClaims(map)
//                        .setIssuedAt(now)                             // 签发时间
//                        .setExpiration(expiryDate)                    // 过期时间
//                        .signWith(privateKey, SignatureAlgorithm.RS256) // 使用私钥签名
//                        .compact();                                   // 生成 Token 字符串
//            } catch (Exception e) {
//                throw new RuntimeException("Failed to generate JWT", e);
//            }
//        }
//    }
//
//
//
//
