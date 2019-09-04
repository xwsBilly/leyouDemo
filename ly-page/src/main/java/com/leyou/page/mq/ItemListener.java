package com.leyou.page.mq;

import com.leyou.page.service.PageService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ItemListener
 *
 * @author Billy
 * @date 2019/9/4 16:34
 */
@Component
public class ItemListener {
    @Autowired
    private PageService pageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.item.insertOrUpdate.queue",durable = "true"),
            exchange = @Exchange(name = "ly.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void listenInsertOrUpdate(Long spuId) {
        if (spuId == null) {
            return;
        }
        // 处理消息，对静态页进行新增
        pageService.syncCreateHtml(spuId);
        // TIPS：无需处理异常，如果发生异常则会被spring捕获，届时ACK不执行，消息回滚，MQ会重试
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.item.delete.queue",durable = "true"),
            exchange = @Exchange(name = "ly.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void listenDelete(Long spuId) {
        if (spuId == null) {
            return;
        }
        // 处理消息，对静态页进行新增或者修改
        pageService.deleteHtml(spuId);
    }
}
