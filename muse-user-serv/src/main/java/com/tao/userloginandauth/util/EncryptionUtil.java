package com.tao.userloginandauth.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class EncryptionUtil {
    private static final String ENCRYPTION_KEY = "GuangXiYiTiHuaPingTaiYongHuRenZhengFuWuXiTong"; // 密钥，必须为16位长度

    public static String encryptLink(String link) throws Exception {
        // 使用 AES 加密链接
        byte[] encryptedBytes = encryptAES(link.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptLink(String encryptedLink) throws Exception {
        // 解码加密后的链接
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedLink);
        // 使用 AES 解密链接
        byte[] decryptedBytes = decryptAES(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private static byte[] encryptAES(byte[] input) throws Exception {
        SecretKey secretKey = generateAESKey();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(input);
    }

    private static byte[] decryptAES(byte[] input) throws Exception {
        SecretKey secretKey = generateAESKey();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(input);
    }

    private static SecretKey generateAESKey() throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(ENCRYPTION_KEY.substring(0,40).getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, "AES");
    }
}

