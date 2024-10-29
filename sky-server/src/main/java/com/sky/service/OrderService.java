package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
   PageResult pageQueryUser(Integer page, Integer pageSize, Integer status);

    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    void cancelOrder(OrdersCancelDTO ordersCancelDTO);

    OrderVO getById(Long id);

    PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    void confirmOrder( OrdersConfirmDTO ordersConfirmDTO);

    void rejectOrder(OrdersRejectionDTO ordersRejectionDTO);

    OrderStatisticsVO getStatistics();

    void completeOrder(Long id);

    void deliverOrder(Long id);

 void cancelOrder4User(Long id);

 void repeatOrder(Long id);
}
