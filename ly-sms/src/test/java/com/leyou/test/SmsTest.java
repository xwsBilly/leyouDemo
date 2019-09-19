package com.leyou.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * SmsTest
 *
 * @author Billy
 * @date 2019/9/9 17:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsTest {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSendSms() {
        Map<String, String> msg = new HashMap<>();
        msg.put("phone","12345678900");
        msg.put("code", "123456");
        amqpTemplate.convertAndSend("ly.sms.exchange","sms.verify.code",msg);
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
