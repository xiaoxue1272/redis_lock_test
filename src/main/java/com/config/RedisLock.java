package com.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author gyj
 * @date 2020年12月09日09点
 * @description
 */
public class RedisLock {

    private RedisTemplate<String, String> redisTemplate;

    private BoundValueOperations<String, String> lock;

    private String key ;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.lock = redisTemplate.boundValueOps(key);
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取redis分布式锁
     */
    public void lock () {
        try {
            logger.info("获取锁");
            lock.set("lock",60, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("获取锁失败:"+e);
            throw e;
        }
    }

    /**
     * 释放redis分布式锁
     */
    public void unLock () {
        try {
            logger.info("释放锁");
            redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("释放锁失败:"+e);
            throw e;
        }
    }

    /**
     * 当前卡批次线程是否在运行
     * @return
     */
    public Boolean hasLock() {
        Boolean result;
        try {
            result = redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("查看锁是否生效失败:"+e);
            throw e;
        }
        return result;
    }

    /**
     * 尝试获取锁
     * @return
     */
    public Boolean tryLock() {
        Boolean result;
        try {
            result = lock.setIfAbsent("lock",60,TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("尝试获取锁失败:"+e);
            throw e;
        }
        return result;
    }
}
