package com.tao.userloginandauth.component;


import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description //TODO  Redis配置
 * Create by 2024/9/2
 */
@Component
public class CustomRedisTemplate extends RedisTemplate<String, Object> {

//    public CustomRedisTemplate() {
//        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);
//
//        setConnectionFactory(redisConnectionFactory());
//        // 使用StringRedisSerializer来序列化和反序列化redis的key值
//        setKeySerializer(new StringRedisSerializer());
//        setValueSerializer(serializer);
//
//        // Hash的key也采用StringRedisSerializer的序列化方式
//        setHashKeySerializer(new StringRedisSerializer());
//        setHashValueSerializer(serializer);
//        afterPropertiesSet();
//    }
//
//    private RedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
////        configuration.setHostName("localhost");
//        configuration.setHostName("172.22.96.137");
//        configuration.setPort(6379);
//        configuration.setPassword("admin123");
//
//        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration);
//        connectionFactory.afterPropertiesSet();
//        return connectionFactory;
//    }
//    public void setWithExpiration(String key, Object value, long expirationInSeconds) {
//        ValueOperations<String, Object> valueOperations = opsForValue();
//        valueOperations.set(key, value, expirationInSeconds, TimeUnit.SECONDS);
//    }



    // 构造函数保持空，不执行任何依赖字段的操作
    public CustomRedisTemplate() {
        // 什么都不做！等 @PostConstruct 初始化
    }

    //
//    @PostConstruct
//    public void init() {
//        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
//        setConnectionFactory(redisConnectionFactory()); // ← 现在调用下面的方法
//        setKeySerializer(new StringRedisSerializer());
//        setValueSerializer(serializer);
//        setHashKeySerializer(new StringRedisSerializer());
//        setHashValueSerializer(serializer);
//        afterPropertiesSet();
//    }
    @PostConstruct
    public void init() {
        // 使用 StringRedisSerializer 来处理 byte[] 和 String
        setConnectionFactory(redisConnectionFactory());
        setKeySerializer(new StringRedisSerializer());
        setValueSerializer(new StringRedisSerializer()); // 👈 改成 StringRedisSerializer
        setHashKeySerializer(new StringRedisSerializer());
        setHashValueSerializer(new StringRedisSerializer());
        afterPropertiesSet();
    }

    private RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("172.22.96.137");      // 此时 @Value 已注入，安全！
        configuration.setPort(6379);
        configuration.setPassword(RedisPassword.of("admin123")); // 推荐用 RedisPassword.of()
        configuration.setDatabase(2);
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration);
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }
}
