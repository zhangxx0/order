package com.xinxin.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 消费端定义的FeignClient
 * value和ProductClient的相同，加了个contextId，于是可以一个服务多个FeignClient
 */
@FeignClient(value = "product", contextId = "product2")
public interface ProductBalanceClient {

    @GetMapping("/loadbanlancoceTest")
    String loadbanlanceTest(@RequestParam(value = "name",required = false) String name);

}
