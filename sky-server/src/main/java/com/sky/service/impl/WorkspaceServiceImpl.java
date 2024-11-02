package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
@Autowired
private OrderMapper orderMapper;
@Autowired
private UserMapper userMapper;
@Autowired
private SetMapper setMapper;
@Autowired
private DishMapper dishMapper;
    @Override
    public BusinessDataVO getBusinessData() {
        LocalDate date = LocalDate.now();
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
        LocalDateTime begin = LocalDateTime.of(date, LocalTime.MIN);
        Map map=new HashMap();
        map.put("begin",begin);
        map.put("end",end);
        //new users
        Integer newUser = userMapper.countByMap(map);
//order completion rate
        Integer totalOrder = orderMapper.countByMap(map);
        map.put("status", Orders.COMPLETED);
        Integer validOrder = orderMapper.countByMap(map);
        Double orderCompletionRate=0.0;
        if(validOrder!=0){
            orderCompletionRate=validOrder.doubleValue()/totalOrder;
        }
        //turnover
        Double turnover = orderMapper.sumByMap(map);
        //unit price
        Double unitPrice=turnover/validOrder;

        return BusinessDataVO.builder().newUsers(newUser).orderCompletionRate(orderCompletionRate)
                .turnover(turnover).unitPrice(unitPrice).validOrderCount(validOrder).build();
    }

    @Override
    public SetmealOverViewVO getOverviewSetmeal() {
       Integer onSale= setMapper.getStatus(1);
       Integer notOnSale= setMapper.getStatus(0);
        return SetmealOverViewVO.builder().sold(onSale).discontinued(notOnSale).build();
    }

    @Override
    public DishOverViewVO getOverviewDish() {
        Integer onSale= dishMapper.getStatus(1);
        Integer notOnSale= dishMapper.getStatus(0);
        return DishOverViewVO.builder().sold(onSale).discontinued(notOnSale).build();
    }

    @Override
    public OrderOverViewVO getOverviewOrder() {
        //订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款
        LocalDate date = LocalDate.now();
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
        LocalDateTime begin = LocalDateTime.of(date, LocalTime.MIN);
        Map map=new HashMap();
        map.put("begin",begin);
        map.put("end",end);
        Integer totalOrder = orderMapper.countByMap(map);
        map.put("status", Orders.CANCELLED);
        Integer cancelOrder = orderMapper.countByMap(map);
map.replace("status",Orders.COMPLETED);
        Integer completeOrder = orderMapper.countByMap(map);
        map.replace("status",Orders.DELIVERY_IN_PROGRESS);
        Integer deliveredOrder = orderMapper.countByMap(map);
        map.replace("status",Orders.TO_BE_CONFIRMED);
        Integer waitingOrder = orderMapper.countByMap(map);

        return OrderOverViewVO.builder().allOrders(totalOrder).cancelledOrders(cancelOrder)
                .completedOrders(completeOrder).deliveredOrders(deliveredOrder).waitingOrders(waitingOrder).build();
    }
}
