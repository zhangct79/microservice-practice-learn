package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangct
 * @date 20/7/17
 */
@RestController
public class IndexController {
    @Autowired
    PoolService poolService;

    @RequestMapping("/index")
    String hello() {
        poolService.invoke();
        return "hello world";
    }
}
