package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api("Dish related api")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
@PostMapping
@ApiOperation("Add dish")
    public Result add(@RequestBody DishDTO dishDTO){
    log.info("Add new dish:{}",dishDTO);
    dishService.addWithFlavor(dishDTO);
    return Result.success();
}

@GetMapping("/page")
@ApiOperation("Dish paging query function")
public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){
   log.info("Dish page query:{}",dishPageQueryDTO);
PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
    return Result.success(pageResult);
}

@DeleteMapping
@ApiOperation("Dishes batch delete")
public Result delete(@RequestParam List<Long> ids){
    log.info("Dishes batch delete:{}",ids);
    dishService.deleteBatch(ids);
    return Result.success();
}

}
