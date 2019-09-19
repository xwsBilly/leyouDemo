package com.leyou.user.service.Impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * UserServiceImplTest
 *
 * @author Billy
 * @date 2019/9/19 14:24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Test
    public void testRandom() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int nextInt = new Random().nextInt(9);
            code.append(nextInt);
        }
        System.out.println("code = " + code);
    }
}