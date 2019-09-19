package com.leyou.user.service.Impl;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.MyException;
import com.leyou.common.utils.NumberUtils;
import com.leyou.user.config.UserProperties;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.pojo.UserVo;
import com.leyou.user.service.UserService;
import com.leyou.user.utils.CodecUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * UserServiceImpl
 *
 * @author Billy
 * @date 2019/9/19 10:43
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserProperties userProperties;

    private static final String KEY_PREFIX = "user:verify:phone:";

    @Override
    public Boolean checkData(String data, Integer type) {
        User record = new User();
        // 判断数据类型
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                throw new MyException(ExceptionEnum.INVALID_USER_DATA_TYPE);
        }

        int count = userMapper.selectCount(record);
        return count == 0;
    }

    @Override
    public void sendCode(String phone) {
        // 生成Redis key
        String key = KEY_PREFIX + phone;
        // 生成验证码
        String code = NumberUtils.generateCode(6);

        Map<String, String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);
        // 发送验证码
        amqpTemplate.convertAndSend(userProperties.getExchange(), userProperties.getQueue(), msg);

        // 保存验证码进Redis，时长5分钟
        redisTemplate.opsForValue().set(key, code, userProperties.getExpireTime(), TimeUnit.MINUTES);
    }

    @Override
    public void register(UserVo userVo) {
        // 从Redis中获取验证码
        String cacheCode = redisTemplate.opsForValue().get(KEY_PREFIX + userVo.getPhone());
        // 校验验证码
        // StringUtils.equals()会判断空指针情况，排除null的情况
        if (!StringUtils.equals(cacheCode, userVo.getCode())) {
            throw new MyException(ExceptionEnum.INVALID_VERIFY_CODE);
        }
        // 对密码加密
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String encode = bCryptPasswordEncoder.encode(userVo.getPassword());

        //生成盐
        String salt = CodecUtils.generateSalt();
        // 对密码加密
        String encode = CodecUtils.md5Hex(userVo.getPassword(), salt);
        //写入数据库
        User user = new User();
        user.setPhone(userVo.getPhone());
        user.setUsername(userVo.getUsername());
        user.setPassword(encode);
        user.setCreated(new Date());
        user.setSalt(salt);
        userMapper.insert(user);
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        // 查询用户
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);
        // 用户不存在
        if (user == null) {
            throw new MyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }
        // 密码不一致
        if (!StringUtils.equals(user.getPassword(), CodecUtils.md5Hex(password, user.getSalt()))) {
            throw new MyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }
        // 用户名和密码正确
        return user;
    }
}
