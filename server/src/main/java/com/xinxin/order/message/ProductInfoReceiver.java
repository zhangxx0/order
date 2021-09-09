package com.xinxin.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.javafx.binding.StringFormatter;
import com.xinxin.order.utils.JsonUtil;
import com.xinxin.pruduct.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductInfoReceiver {

    private static final String PRODUCT_STOCK_TEMPLETE = "product_stock_%s";

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message) {
        // message -> ProductInfoOutput
        List<ProductInfoOutput> productInfoOutputList = (List<ProductInfoOutput>) JsonUtil.fromJson(message,
                new TypeReference<List<ProductInfoOutput>>() {});

        log.info("从队列【{}】接收到消息：{}", "productInfo", productInfoOutputList);

        // 存储到redis
        for (ProductInfoOutput productInfoOutput : productInfoOutputList) {
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLETE, productInfoOutput.getProductId()),
                    String.valueOf(productInfoOutput.getProductStock()));

        }

    }

}
