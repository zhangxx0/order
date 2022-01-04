package com.xinxin.order.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xinxin.order.service.SentinelTestService;
import com.xinxin.order.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SentinelTestServiceImpl implements SentinelTestService {

    // blockHandler 是位于 ExceptionUtil 类下的 handleException 静态方法，需符合对应的类型限制.
    // 这里单独演示 blockHandlerClass 的配置.
    // 对应的 `handleException` 函数需要位于 `ExceptionUtil` 类中，并且必须为 public static 函数.
    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    public void test() {
        System.out.println("Test");
    }

    // blockHandler 是位于当前类下的 exceptionHandler 方法，需符合对应的类型限制.
    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    @Override
    @SentinelResource(value = "sayHello", blockHandler = "exceptionHandler", fallback = "helloFallback")
    public String sayHello(String name) {
        return "hello " + name;
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(String name, BlockException e) {
        // Do some log here.
        log.info("开始限流~~~~");
        e.printStackTrace();
        return "Oops, error occurred at " + name;
    }

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String helloFallback(String name) {
        log.info("开始fallback~~~~");
        return "helloFallback" + name;
    }
}
