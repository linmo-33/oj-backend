package com.linmo.oj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置类
 * @author ljl
 * @since 2023-12-06 14:25
 */
@Slf4j
@Configuration
@ConditionalOnClass(RedisTemplate.class)
public class RedisConfig {
    /**
     * 创建 RedisTemplate Bean，用于操作 Redis 数据库。
     *
     * @param connectionFactory Redis 连接工厂
     * @return RedisTemplate 实例
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置 RedisTemplate 的连接工厂
        redisTemplate.setConnectionFactory(connectionFactory);
        // 创建 StringRedisSerializer 实例，用于序列化和反序列化 Redis 的键和哈希键
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 创建 GenericJackson2JsonRedisSerializer 实例，用于序列化和反序列化 Redis 的值和哈希值
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        log.info("RedisTemplate init...");
        // 设置默认序列化器为 StringRedisSerializer
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        // 设置 RedisTemplate 的键序列化器为 StringRedisSerializer
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // 设置 RedisTemplate 的哈希键序列化器为 StringRedisSerializer
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // 设置 RedisTemplate 的值序列化器为 GenericJackson2JsonRedisSerializer
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        // 设置 RedisTemplate 的哈希值序列化器为 GenericJackson2JsonRedisSerializer
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }

}
