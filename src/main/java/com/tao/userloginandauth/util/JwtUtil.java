package com.tao.userloginandauth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    @Value("${privateKeyPem}")
    private String privateKeyPem;

    @Value("${publicKeyPem}")
    private String publicKeyPem;

    private String jwtPublicKey;
    private String jwtPrivateKey;

    @PostConstruct
    public void init() throws IOException {
        // 确保配置文件中 privateKeyPem / publicKeyPem 是文件路径（如 /etc/keys/public.pem）
        this.jwtPublicKey = new String(Files.readAllBytes(Paths.get(publicKeyPem)), StandardCharsets.UTF_8);
        this.jwtPrivateKey = new String(Files.readAllBytes(Paths.get(privateKeyPem)), StandardCharsets.UTF_8);
    }

    public PublicKey getPublicKeyFromPem(String pemPublicKey) {
        try {
            String publicKeyPEM = pemPublicKey
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load public key", e);
        }
    }

    public PrivateKey getPrivateKeyFromPem(String pemPrivateKey) {
        try {
            String privateKeyPEM = pemPrivateKey
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key", e);
        }
    }

    public Claims verifyJwt(String token) {
        try {
            PublicKey publicKey = getPublicKeyFromPem(jwtPublicKey);
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("JWT verification failed", e);
        }
    }

    public String generateJwt(Map<String, ?> claims, long expirationMs) {
        try {
            PrivateKey privateKey = getPrivateKeyFromPem(jwtPrivateKey);
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + expirationMs);
            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(privateKey, SignatureAlgorithm.RS256)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT", e);
        }
    }
}
