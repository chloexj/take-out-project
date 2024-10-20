package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetDishMapper;
import com.sky.mapper.SetMapper;
import com.sky.result.PageResult;
import com.sky.service.SetService;
import com.sky.vo.SetmealVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class SetServiceImpl implements SetService {
    @Autowired
    private SetMapper setMapper;
    @Autowired
    private SetDishMapper setDishMapper;

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void add(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(StatusConstant.DISABLE);
        setMapper.insert(setmeal);
        //给setmeal_dish表也加内容
        Long setmealId = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
        setDishMapper.insert(setmealDishes);

    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            if (setMapper.getById(id).getStatus() == 1) {
//on sales
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

        setMapper.deleteBatch(ids);
        setDishMapper.deleteBatch(ids);
    }

    @Override
    public SetmealVO getBySetId(Long id) {
        //setmeal 一个表         //category name  需要另查一个表?
        SetmealVO setmealVO = new SetmealVO();
        Setmeal setmeal = setMapper.getById(id);
        BeanUtils.copyProperties(setmeal, setmealVO);

        //setmeal dish 对象 又要再查一个表
        List<SetmealDish> setmealDishes = setDishMapper.getBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setMapper.update(setmeal);
        //还要把setmeal_dish的表删掉再导
        Long setmealId = setmealDTO.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
        //删掉setmeal_dish中关联了ID的数据
        setDishMapper.deleteBatch(Collections.singletonList(setmealId));
        //再导入
        setDishMapper.insert(setmealDishes);
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        Setmeal setmeal=Setmeal.builder().id(id).status(status).build();
        setMapper.update(setmeal);
    }
}
