package com.leyou.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * com.leyou.test.redisTest
 *
 * @author Billy
 * @date 2019/9/9 15:38
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        // 存储数据
        redisTemplate.opsForValue().set("key1", "value1");
        // 获取数据
        String key1 = redisTemplate.opsForValue().get("key1");
        System.out.println("key1 = " + key1);
    }

    @Test
    public void testRedis2() {
        // 存储数据，并制定剩余生命时间，5小时
        redisTemplate.opsForValue().set("key2", "value2", 5, TimeUnit.HOURS);
    }

    @Test
    public void testHash() {
        BoundHashOperations<String, Object, Object> user = redisTemplate.boundHashOps("user");
        // 操作hash数据
        user.put("name", "jack");
        user.put("age", "21");

        // 获取单个数据
        Object name = user.get("name");
        System.out.println("name = " + name);

        // 获取所有数据
        Map<Object, Object> entries = user.entries();
        if (entries != null) {
            for (Map.Entry<Object, Object> objectObjectEntry : entries.entrySet()) {
                System.out.println(objectObjectEntry.getKey() + ":" + objectObjectEntry.getValue());
            }
        }
    }
}
