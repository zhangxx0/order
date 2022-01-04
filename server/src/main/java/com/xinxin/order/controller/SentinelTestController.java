package com.xinxin.order.controller;

import com.xinxin.order.service.SentinelTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentinelTestController {

    @Autowired
    SentinelTestService sentinelTestService;

    @GetMapping(value = "/test")
    public void test() {
        sentinelTestService.test();
    }

    @GetMapping(value = "/hello/{name}")
    public String apiHello(@PathVariable String name) {
        return sentinelTestService.sayHello(name);
    }

}
