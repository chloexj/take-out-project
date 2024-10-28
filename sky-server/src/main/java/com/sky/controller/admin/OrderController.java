package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
@Slf4j
@Api(tags = "Order admin related api")
public class OrderController {
@Autowired
private OrderService orderService;
    //cancel order
@PostMapping("/cancel")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO){
log.info("Cancel order:{}",ordersCancelDTO);
orderService.cancelOrder(ordersCancelDTO);
        return Result.success();
    }
    @GetMapping("/details/{id}")
    public Result<OrderVO> checkOrders(@PathVariable Long id){
OrderVO orderVO= orderService.getById(id);
return Result.success(orderVO);
    }
@GetMapping("/conditionSearch")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO){
log.info("Order page query:{}",ordersPageQueryDTO);
PageResult pageResult = orderService.pageQuery(ordersPageQueryDTO);
return Result.success(pageResult);


}


}
