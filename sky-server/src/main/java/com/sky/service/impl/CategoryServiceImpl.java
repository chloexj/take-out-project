package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
@Autowired
    private  DishMapper dishMapper;
@Autowired
private SetMapper setMapper;
@Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        long total = page.getTotal();
        List<Category> records = page.getResult();
        return new PageResult(total, records);
    }

    @Override
    public void updateInfo(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        categoryMapper.update(category);
    }

    //update status
    @Override
    public void updateStatus(Integer status, Long id) {
        Category category =
                Category.builder().status(status).id(id).build();
        categoryMapper.update(category);
    }

    @Override
    public void add(CategoryDTO categoryDTO) {

        Category category =
                Category.builder().updateTime(LocalDateTime.now())
                        .status(0)
                        .build();
        BeanUtils.copyProperties(categoryDTO, category);

        categoryMapper.insert(category);
    }

    @Override
    public void delete(Long id) {
        Integer count= dishMapper.countByCategoryId(id);
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        count=setMapper.countByCategoryId(id);
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        categoryMapper.delete(id);
    }

    @Override
    public List<Category> getByType(Integer type) {
        List<Category> list= categoryMapper.get(type);
        return list;
    }
}
