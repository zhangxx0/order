package com.xinxin.order.service.impl;

import com.xinxin.order.dataobject.OrderDetail;
import com.xinxin.order.dataobject.OrderMaster;
import com.xinxin.order.dto.OrderDTO;
import com.xinxin.order.enums.OrderStatusEnum;
import com.xinxin.order.enums.PayStatusEnum;
import com.xinxin.order.repository.OrderDetailRepository;
import com.xinxin.order.repository.OrderMasterRepository;
import com.xinxin.order.service.OrderService;
import com.xinxin.order.utils.KeyUtil;
import com.xinxin.product.client.ProductClient;
import com.xinxin.pruduct.common.DecreaseStockInput;
import com.xinxin.pruduct.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();

        // 查询商品信息(调用商品服务)
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfoOutput> productInfoList = productClient.listForOrder(productIdList);

        // 计算总价
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfoOutput productInfo : productInfoList) {
                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
                    orderAmout = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmout);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    // 订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        // 扣库存(调用商品服务)
        List<DecreaseStockInput> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(cartDTOList);

        // 订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
