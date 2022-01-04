package com.xinxin.order.utils;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class ExceptionUtil {

    public static void handleException(BlockException ex) {
        System.out.println("Oops: " + ex.getClass().getCanonicalName());
    }

}
