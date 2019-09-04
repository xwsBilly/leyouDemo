package com.leyou.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HelloController
 *
 * @author xieweisong
 * @date 2019/9/2 10:31
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String toHello(Model model){
        model.addAttribute("msg", "hello thymeleaf");
        return "hello";
    }
}
