package com.xinxin.order.service;

import com.xinxin.order.dto.OrderDTO;

public interface OrderService {


    OrderDTO create(OrderDTO orderDTO);

    OrderDTO finish(String orderId);

}
