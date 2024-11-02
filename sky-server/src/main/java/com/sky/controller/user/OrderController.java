package com.sky.controller.user;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Api(tags = "User order related api")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/submit")
    @ApiOperation("submit order")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("User place order:{}",ordersSubmitDTO);
      OrderSubmitVO orderSubmitVO= orderService.submitOrder(ordersSubmitDTO);

        return Result.success(orderSubmitVO);
    }

    @GetMapping("/historyOrders")
    public  Result<PageResult> pageQuery(int page, int pageSize, Integer status){
      PageResult pageRes = orderService.pageQueryUser(page,pageSize,status);

        return Result.success(pageRes);
    }

@GetMapping("/orderDetail/{id}")
    public Result<OrderVO> getByOrderId(@PathVariable Long id){
    OrderVO orderVO = orderService.getById(id);

    return Result.success(orderVO);
}

@PutMapping("/cancel/{id}")
    public Result cancelOrder(@PathVariable Long id){
        orderService.cancelOrder4User(id);
        return Result.success();
}

@PostMapping("/repetition/{id}")
    public Result repeatOrder(@PathVariable Long id){
        orderService.repeatOrder(id);
        return null;
}

@GetMapping("/reminder/{id}")
    public Result reminder(@PathVariable Long id){
        log.info("User {} is pushing the order",id);
        orderService.reminder(id);

        return Result.success();
}
}
