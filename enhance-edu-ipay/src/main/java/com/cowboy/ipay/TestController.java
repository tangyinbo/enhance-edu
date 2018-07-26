package com.cowboy.ipay;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/7/25 14:01
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/test1")
    public String test1(){
        return "tangyinbo";
    }
}
