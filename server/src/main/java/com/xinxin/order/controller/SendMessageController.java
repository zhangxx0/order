package com.xinxin.order.controller;

import com.xinxin.order.dto.OrderDTO;
import com.xinxin.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

    /**
     * 测试请求的发送
     *
     * http://localhost:8081/sendMessage
     *//*
    @GetMapping("/sendMessage")
    public void process() {
        String messaee = "Now: " + new Date();
        streamClient.output().send(MessageBuilder.withPayload(messaee).build());
    }*/

    /**
     * 发送对象
     */
    @GetMapping("/sendMessage")
    public void process() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("123456");
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }

}
