package com.controller;

import com.config.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private RedisLock redisLock;

    @Autowired
    public void setRedisLock(RedisLock redisLock) {
        this.redisLock = redisLock;
    }

    @GetMapping("lock")
    public Boolean lock() {
        redisLock.lock();
        return redisLock.hasLock();
    }

    @GetMapping("unLock")
    public Boolean unLock() {
        redisLock.unLock();
        return redisLock.hasLock();
    }

    @GetMapping("tryLock")
    public Boolean tryLock() {
        return redisLock.tryLock();
    }

    @GetMapping("hasLock")
    public Boolean unlock() {
        return redisLock.hasLock();
    }
}
