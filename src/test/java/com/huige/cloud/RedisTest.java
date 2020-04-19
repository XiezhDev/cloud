package com.huige.cloud;

import com.huige.cloud.test.redis.RedisTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTestService redisTestService;

    @Test
    public void testLog() {
        redisTestService.getUserByName("admin");
    }
}
