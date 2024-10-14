package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags="Employee related api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "employee login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "employee logout")
    public Result<String> logout() {
        return Result.success();
    }

    // create new employee
    @PostMapping
    @ApiOperation("Create new employee")
    public Result create(@RequestBody EmployeeDTO employeeDTO){
        log.info("Create new employee:{}",employeeDTO);
        System.out.println("Current thread id:"+Thread.currentThread().getId());
        employeeService.create(employeeDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("Employee paging query")
    //因为不是json所以不用加request body
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
log.info("employee paging query:{}",employeePageQueryDTO);
PageResult pageResult=employeeService.pageQuery(employeePageQueryDTO);
return Result.success(pageResult);

    }

    //非查询类就不用泛型了
    @PostMapping("/status/{status}")
    @ApiOperation("Change employee status")
    public Result changeStatus(@PathVariable Integer status, Long id){
        log.info("Change employee status:{},{}",status,id);
        employeeService.changeStatus(status,id);
        return Result.success();
    }
}
