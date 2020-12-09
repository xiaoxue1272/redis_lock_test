package com;

import com.config.RedisLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@ConfigurationProperties(
        prefix = "redis.lock"
)
public class LockApplication {

    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    public static void main(String[] args) {
        SpringApplication.run(LockApplication.class,args);
    }

    @Bean
    public RedisLock redisLock(RedisTemplate<String,String> redisTemplate){
        RedisLock lock = new RedisLock();
        lock.setKey(key);
        lock.setRedisTemplate(redisTemplate);
        return lock;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        template.setStringSerializer(new StringRedisSerializer());
        return template;
    }


}
