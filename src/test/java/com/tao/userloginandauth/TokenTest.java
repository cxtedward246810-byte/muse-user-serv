//package com.tao.userloginandauth;
//
//import com.tao.userloginandauth.util.JwtUtil;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import jar.org.apache.pdfbox.io.RandomAccessFileOutputStream;
//import org.junit.jupiter.api.Test;
//import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
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
// * @Date 2025/9/17
// */
//@SpringBootTest
//public class TokenTest {
//    @Test
//    public void testyToken() throws IOException {
//        byte[] bytes = Files.readAllBytes(Paths.get("D:\\test\\demo\\publicKey.pem"));
//        String string = new String(bytes, StandardCharsets.UTF_8);
//
////        Claims claims = JwtUtil.parseJWT("eyJhbGciOiJUzl1NilsInR5cCl6lkpXVCJ9.eyJ1c2VySWQiOi3NmI4MTNmMizNiBKLTOwOTGtODkxOSOzNmVIODJNYzC30DMiLCI1c2VyTmftZSl6lnpvdXihb2ppliwidG9rZW5JZCI6IiK2MZU4NGEzOGUOMZU2ZmJIMWMWOTNhMTA0ZDJhZGU5NGEXOTA3M212ZjBkOTYyMilslmlhdCI6MTc1NzY0ODg3MiwiZhwlioxNzU4MiUzNicyfO.MANGR7AxaPiHXY0WYTG60M32zEkvdaNDaxBD3JDrОA");
////        Claims claims = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI3NmI4MTNmMi0zNjBkLTQwOTgtODkxOS0zNmVlODJlYzc3ODMiLCJ1c2VyTmFtZSI6InpvdXlhb2ppIiwidG9rZW5JZCI6IjU4NmU3NzE3NWRlYWFkYzg4NDIzMTEyYWViNWJkNjFhNjI2M2M1NGQ0M2Y4MDIzNSIsImlhdCI6MTc1ODA4MjIwMywiZXhwIjoxNzU4Njg3MDAzfQ.VEpGKWQD8NUvH2cXH5-6b5JrGKJAsqYQPVWDE3P_z0Q");
////        Claims claims = verifyJwt("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI3NmI4MTNmMi0zNjBkLTQwOTgtODkxOS0zNmVlODJlYzc3ODMiLCJ1c2VyTmFtZSI6InpvdXlhb2ppIiwidG9rZW5JZCI6Ijc0MWU3NTAyOGE2MTgyZTBkYjg5ZDg1ODliMDQwYzcyZWYxYmUxNmQ4ZDg3ZTBlOCIsImlhdCI6MTc1ODE4MjI2OSwiZXhwIjoxNzU4Nzg3MDY5fQ.DkJLPPKGm9MITKuowNGzGLUQdaJc_3NO7PL7gInWhn-nAf25tJJgClPUxPfeyVY65rR6XkgGUgdAwXfovgPXh9nRlfdrMfRghnAO9MjV4WdvHkTZJfjhgpO6HEOxqO01dAt82x-Pb5lJQzuDsZGo7p6h_f1IYQzEJW31uzCctwkX7Xu77UK1TKvwZ8s200o0yXoQmQcGA8BBD0GYlys8mkVabW8dzqS7U5XruIZyP29GTsXHgCo87VaFinlMbP_RRS1xtgVrvDYIPU_2wll0G7NlmjD4UC_x_3eS2XAaEPKixxgXhCPLnWJQSUCAtDVyP0eCvDv93JB4_bnIsA2LUA",string);
//        //eyJhbGciOiJSUzI1NiJ9.eyJ1c2VySW5mbyI6InsgICAgICAgICAgICAgXCJkZXBhcnRJRFwiOiAxLCAgICAgICAgICAgICBcImFyZWFJRFwiOiAxLCAgICAgICAgICAgICBcImRlcGFydE5hbWVcIjogXCLlub_opb9cIiwgICAgICAgICAgICAgXCJwYXJlbnRJRFwiOiAwLCAgICAgICAgICAgICBcImRlcGFydENvZGVcIjogXCI0NVwiLCAgICAgICAgICAgICBcImRlcGFydExldmVsXCI6IDAsICAgICAgICAgICAgIFwiY29kZU9mVG93bkZvcmVjYXN0XCI6IFwiQkVOTlwiLCAgICAgICAgICAgICBcImNvZGVPZkd1aWRhbmNlRm9yZWNhc3RcIjogXCJOTlwiLCAgICAgICAgICAgICBcImNvZGVPZkRMXCI6IFwiR1hETFwiLCAgICAgICAgICAgICBcInVzZXJOYW1lXCI6IFwiZ3Vhbmd4aVwiLCAgICAgICAgICAgICBcImlkXCI6IFwiZ3Vhbmd4aS11dWlkXCIsICAgICAgICAgICAgIFwibmFtZVwiOiBcImd1YW5neGlcIiwgICAgICAgICAgICAgXCJwb3N0XCI6IFwi55-t5Li05bKXXCIsICAgICAgICAgICAgIFwic2hvd05hbWVcIjogXCLlub_opb_msJTosaHlj7BcIiwgICAgICAgICAgICAgXCJhcmVhQ29kZVwiOiA0NTAwMDAsICAgICAgICAgICAgIFwicG9zdExpc3RcIjogWyAgICAgICAgICAgICAgICAgeyAgICAgICAgICAgICAgICAgICAgIFwidmFsdWVcIjogXCLnn63kuLTlspdcIiwgICAgICAgICAgICAgICAgICAgICBcImxhYmVsXCI6IFwi55-t5Li05bKXXCIgICAgICAgICAgICAgICAgIH0sICAgICAgICAgICAgICAgICB7ICAgICAgICAgICAgICAgICAgICAgXCJ2YWx1ZVwiOiBcIuS4reefreacn-Wyl1wiLCAgICAgICAgICAgICAgICAgICAgIFwibGFiZWxcIjogXCLkuK3nn63mnJ_lspdcIiAgICAgICAgICAgICAgICAgfSwgICAgICAgICAgICAgICAgIHsgICAgICAgICAgICAgICAgICAgICBcInZhbHVlXCI6IFwi5Yaz562W5pyN5Yqh5bKXXCIsICAgICAgICAgICAgICAgICAgICAgXCJsYWJlbFwiOiBcIuWGs-etluacjeWKoeWyl1wiICAgICAgICAgICAgICAgICB9LCAgICAgICAgICAgICAgICAgeyAgICAgICAgICAgICAgICAgICAgIFwidmFsdWVcIjogXCLlnLDluILlspdcIiwgICAgICAgICAgICAgICAgICAgICBcImxhYmVsXCI6IFwi5Zyw5biC5bKXXCIgICAgICAgICAgICAgICAgIH0gICAgICAgICAgICAgXSAgICAgICAgIH0iLCJhZG1pbkNvZGUiOiI0NTAwMDAiLCJ1c2VyTmFtZSI6Imd1YW5neGkiLCJ1c2VyUm9sZSI6MSwiaWF0IjoxNzYxMDE0MzQyLCJleHAiOjE3NjEyNzM1NDJ9.HewJQSdk6ktJQu0xu0wp5ZBvQMY2ALPMWEdULRxAaDuJ-o8v8YNNDVT-TkWskIe30Ui4vNYcLxRGOUiR2v4GZnGwTpNiehNzJTxCgmUG-PWh2ELTPs3KisDjNjNF-NRspmJfwursVcCIFGrF_0YGfb_lnXzCppsdmaE7mzSagcquVbblx0DPVEQncJztgyDaP06Mb_Glv9jQrm2t6MVH8I7p51qSmtmlBvfEtQKL-v_6XFCfN_gv8_VbcjJkNntusZOCoe-Xdc0hWtkjqAHa0bgdtxlMhpE23hbkx3MWPiccMZ64vrDFGV25fpvYyxwpIaO_vnLRq2BUM0M20pL0Dg
//        Claims claims = verifyJwt("eyJhbGciOiJSUzI1NiJ9.eyJ1c2VyUm9sZXMiOlsxLDJdLCJ0b2tlbklkIjoiOTM0NDI0NjY4OTAxNzU5NCIsImFkbWluQ29kZSI6IjQ1MDAwMCIsInVzZXJOYW1lIjoiYWRtaW4iLCJpYXQiOjE3NjEwMzIyMzQsImV4cCI6MTc2MTI5MTQzNH0.j7pSqDA8S2qRcZtHPPVWh3jx1HVGNZnIadPs2ghOnopX2Y2EmomB8hObqhtqcOmeRQBfnXqepSe1x4xq7S4zlfz85d7zc4Xywdg-v0zuyT2TCjQlJxvBWP-gHCt6kibhTYmupzrOomdttDlPdly8z6ZVN0Ymag-5Ui4jCmubTTmXqGeOumfkLhtpYuTL0daEyE1jKPGnb8sz2GCTs8JlXQLWiBF52iJ708zIlAS5Tia5CT01Cb2rTdetSnqo-AE4WJuOU5F4ONgOJVDSm5IuNVNeyT3lP2ohOcZ2XbB5Q1U48kJzEAw7warP4g1MRoIlR8XBIz2JWo7j8ILHHUbfqw",string);
////        Claims claims = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlMzExYWEyYS1iMTE1LTQ3ZDAtOTYwMy1jYzE1MWIzZWFhYTAiLCJzdWIiOiJ3cWRqa3dsMTJlMmRkMUZRbGsxajJkYXNkYWRkbXpzYWRhYWQyem91eWFvamkiLCJpYXQiOjE3NTgxNTg4MjQsImV4cCI6MTc1ODc2MzYyNH0.TgLetngl_wWy-zw4DS9lhd7LGxiz1qDuB6hrw36ht5s");
//
//        System.out.println(claims);
//    }
//
//    @Test
//    public void testcToken() throws IOException {
////        String token = JwtUtil.createAccessToken("casdcsadcsdcsdcsdcsadcsd");
////        System.out.println("token");
////        System.out.println(token);
//        //userId=76b813f2-360d-4098-8919-36ee82ec7783, userName=zouyaoji, tokenId=741e75028a6182e0db89d8589b040c72ef1be16d8d87e0e8
//        Map<String, Object> hashMap = new HashMap<>();
//        hashMap.put("userId","76b813f2-360d-4098-8919-36ee82ec7783");
//        hashMap.put("userName","pux");
//        hashMap.put("tokenId","741e75028a6182e0db89d8589b040c72ef1be16d8d87e0e8");
//        byte[] bytes = Files.readAllBytes(Paths.get("D:\\test\\demo\\privateKey.pem"));
//        String string = new String(bytes, StandardCharsets.UTF_8);
//        String string1 = generateJwt(string, hashMap, 100000 * 24 * 60 * 60);
//        System.out.println(string1);
//    }
//
//
//    public static PublicKey getPublicKeyFromPem(String pemPublicKey) {
//        try {
//            // 移除 PEM 文件中的头尾标记和换行符
//            String publicKeyPEM = pemPublicKey
//                    .replace("-----BEGIN PUBLIC KEY-----", "")
//                    .replace("-----END PUBLIC KEY-----", "")
//                    .replaceAll("\\s", "");
//            byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
//            return keyFactory.generatePublic(keySpec);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to load public key", e);
//        }
//    }
//
//    public static Claims verifyJwt(String token, String publicKeyPem) {
//        try {
//            PublicKey publicKey = getPublicKeyFromPem(publicKeyPem);
//
//            return Jwts.parserBuilder()
//                    .setSigningKey(publicKey)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (Exception e) {
//            throw new RuntimeException("JWT verification failed", e);
//        }
//    }
//
//
//    // ✅ 从 PEM 格式私钥字符串加载 PrivateKey
//    public static PrivateKey getPrivateKeyFromPem(String pemPrivateKey) {
//        try {
//            // 移除头尾标记和所有空白字符
//            String privateKeyPEM = pemPrivateKey
//                    .replace("-----BEGIN PRIVATE KEY-----", "")
//                    .replace("-----END PRIVATE KEY-----", "")
//                    .replaceAll("\\s", "");
//
//            byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
//
//            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//            return keyFactory.generatePrivate(keySpec);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to load private key", e);
//        }
//    }
//
//    // ✅ 使用私钥生成 JWT Token
//    public static String generateJwt(String privateKeyPem, Map<String, ?> map, long expirationMs) {
//        try {
//            PrivateKey privateKey = getPrivateKeyFromPem(privateKeyPem);
//
//            // 设置过期时间
//            Date now = new Date();
//            Date expiryDate = new Date(now.getTime() + expirationMs);
//            // 创建 JWT
//            return Jwts.builder()
//                    .setClaims(map)
//                    .setIssuedAt(now)                             // 签发时间
//                    .setExpiration(expiryDate)                    // 过期时间
//                    .signWith(privateKey, SignatureAlgorithm.RS256) // 使用私钥签名
//                    .compact();                                   // 生成 Token 字符串
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate JWT", e);
//        }
//    }
//}
//
