package com.xinxin.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyReceiver {

    //1. @RabbitListener(queues = "myQueue") 手动创建或已有队列
    //2. 自动创建队列 @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //3. 自动创建, Exchange和Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void proccess(String message) {
        log.info("MyReceiver:{}", message);
    }

    /**
     * 数码供应商服务 接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
    ))
    public void proccessComputer(String message) {
        log.info("ComputerReceiver:{}", message);
    }

    /**
     * 水果供应商服务 接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitOrder")
    ))
    public void proccessFruit(String message) {
        log.info("FruitReceiver:{}", message);
    }

}
