package com.leyou.sms.mq;

import com.aliyuncs.exceptions.ClientException;
import com.leyou.common.utils.JsonUtils;
import com.leyou.sms.config.SmsProperties;
import com.leyou.sms.utils.SmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * ItemListener
 *
 * @author Billy
 * @date 2019/9/4 15:53
 */
@Slf4j
@Component
public class SmsListener {
    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties smsProperties;

    /**
     * 发送短信验证码
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sms.verify.code.queue",durable = "true"),
            exchange = @Exchange(name = "ly.sms.exchange",type = ExchangeTypes.TOPIC),
            key = {"sms.verify.code"}
    ))
    public void listenSendSms(Map<String,String> msg) {
        if (CollectionUtils.isEmpty(msg)) {
            return;
        }
        // remove删除对象，并且获取被删除的对象
        String phone = msg.remove("phone");
        if (StringUtils.isBlank(phone)){
            return;
        }
        // 处理消息
        smsUtils.sendSms(phone,smsProperties.getSignName(),smsProperties.getVerifyCodeTemplate(), JsonUtils.toString(msg));
    }

}
