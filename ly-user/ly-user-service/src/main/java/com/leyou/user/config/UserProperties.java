package com.leyou.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * UserProperties
 *
 * @author Billy
 * @date 2019/9/19 14:38
 */
@Data
@Component
@ConfigurationProperties(prefix = "ly.user")
public class UserProperties {
    /**
     * rabbitmq交换机
     */
    String exchange;

    /**
     * rabbitmq队列
     */
    String queue;

    /**
     * redis过期时间
     */
    Long expireTime;
}
