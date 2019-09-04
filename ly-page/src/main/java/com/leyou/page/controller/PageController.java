package com.leyou.page.controller;

import com.leyou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * PageController
 *
 * @author xieweisong
 * @date 2019/9/2 17:25
 */
@Controller
public class PageController {
    @Autowired
    private PageService pageService;

    @GetMapping("/item/{id}.html")
    public String toItemPage(@PathVariable("id") Long spuId, Model model) {
        // 查询模型数据
        Map<String,Object> attributes=pageService.loadModel(spuId);
        // 准备模型数据
        model.addAllAttributes(attributes);
        // 判断是否需要生成新的页面
        if (!this.pageService.exists(spuId)){
            this.pageService.syncCreateHtml(spuId);
        }
        // 返回视图
        return "item";
    }
}
