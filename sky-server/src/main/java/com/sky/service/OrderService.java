package com.sky.service;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    void cancelOrder(OrdersCancelDTO ordersCancelDTO);

    OrderVO getById(Long id);
}
