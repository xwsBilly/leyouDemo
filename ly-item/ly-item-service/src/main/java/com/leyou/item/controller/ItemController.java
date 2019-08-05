package com.leyou.item.controller;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.MyException;
import com.leyou.item.pojo.Item;
import com.leyou.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用异常测试demo
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    // 返回http响应实体
    public ResponseEntity<Item> saveItem(Item item){
        // 校验价格
        if (item.getPrice()==null){
            // 参数有误，返回400
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

            // 将异常抛出，由通用异常处理类做处理
            throw new MyException(ExceptionEnum.PRICE_CAN_NOT_BE_NULL);
        }
        item=itemService.saveItem(item);
        // 新增成功，返回201
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
}
