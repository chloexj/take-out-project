package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

@GetMapping("/{id}")
@ApiOperation("Set meal get by id")
public Result<SetmealVO> getById(@PathVariable Long id) {
    SetmealVO setmealVO = setService.getBySetId(id);
    return Result.success(setmealVO);
}

    @DeleteMapping
    @ApiOperation("Setmeal dish batch delete function")
    public Result deleteByIds(@RequestParam List<Long> ids){
        log.info("batch delete function:{}",ids);
        setService.deleteByIds(ids);
        return Result.success();
    }
@PutMapping
@ApiOperation("update setmeal function")
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("update setmeal:{}",setmealDTO);
        setService.update(setmealDTO);
        return Result.success();
}
@PostMapping("/status/{status}")
@ApiOperation("update status")
public Result updateStatus(@PathVariable Integer status, Long id){
        log.info("Update Status:{},{}",status,id);
setService.updateStatus(status,id);
return Result.success();
    }
}
