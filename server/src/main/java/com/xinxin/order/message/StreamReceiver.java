package com.xinxin.order.message;

import com.xinxin.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableBinding(StreamClient.class)
public class StreamReceiver {

    /*@StreamListener(value = StreamClient.INPUT)
    public void proccess(Object message) {
        log.info("StreamReceiver: {}", message);
    }*/

    @StreamListener(value = StreamClient.INPUT)
    @SendTo(StreamClient.INPUT2)
    public String process(OrderDTO message) {
        log.info("StreamRecerver1: {}", message);
        return "received.";
    }

    @StreamListener(value = StreamClient.INPUT2)
    public void process2(String message) {
        log.info("StreamReceiver2: {}", message);
    }

}
