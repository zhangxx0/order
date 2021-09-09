package com.xinxin.order.message;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class MyReceiverTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    void sendProccess() {
        amqpTemplate.convertAndSend("myQueue", "now " + new Date());
    }

    @Test
    void sendOrderProccess() {
        amqpTemplate.convertAndSend("myOrder", "computer", "Now " + new Date());
        amqpTemplate.convertAndSend("myOrder", "fruit", "Now " + new Date());
    }
}