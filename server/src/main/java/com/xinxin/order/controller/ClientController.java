package com.xinxin.order.controller;

import com.xinxin.order.client.ProductBalanceClient;
import com.xinxin.product.client.ProductClient;
import com.xinxin.pruduct.common.DecreaseStockInput;
import com.xinxin.pruduct.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {

//    @Autowired
//    private LoadBalancerClient loadBalancerClient;

    @Autowired
    ProductClient productClient;

    @Autowired
    ProductBalanceClient productBalanceClient;

    /**
     * http://localhost:8081/getProductMsg
     * <p>
     * 测试服务间使用RestTemplate调用
     * return: this is product' msg
     * 2021年8月13日15:09:13
     *
     * @return
     */
    @GetMapping("/getProductMsg")
    public String getProductMsg() {

        /*RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s", serviceInstance.getHost(),serviceInstance.getPort() + "/msg");
        String response = restTemplate.getForObject(url, String.class);*/

        String response = productClient.getMsg();

        return response;
    }

    @GetMapping("/getProductList")
    public String getProductList() {
        List<ProductInfoOutput> list = productClient.listForOrder(Arrays.asList("157875196366160022", "157875227953464068"));
        log.info("response={}", list);
        return "ok";
    }

    @GetMapping("/decreaseStock")
    public String decreaseStock() {
        productClient.decreaseStock(Arrays.asList(new DecreaseStockInput("164103465734242707", 10)));
        return "ok";
    }

    /**
     * feign负载均衡测试
     * @param name
     * @return
     */
    @GetMapping("/loadbanlanceTest")
    public String loadbanlanceTest(@RequestParam(name = "name") String name) {
        return productBalanceClient.loadbanlanceTest(name);
    }

}
