package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Set related")
@Slf4j
@RequestMapping("/admin/setmeal")
public class SetController {
    @Autowired
    private SetService setService;

    @ApiOperation("Set meal paging query")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("set meal page query:{}", setmealPageQueryDTO);
        PageResult pageResult = setService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
@PostMapping
@ApiOperation("Set meal add function")
    public Result add(@RequestBody SetmealDTO setmealDTO){
       log.info("add setmeal:{}",setmealDTO);
        setService.add(setmealDTO);
        return Result.success();
}


}
