package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
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
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }

    //update status
    @Override
    public void updateStatus(Integer status, Long id) {
        Category category =
                Category.builder().status(status).id(id).updateTime(LocalDateTime.now())
                        .updateUser(BaseContext.getCurrentId()).build();
        categoryMapper.update(category);
    }

    @Override
    public void add(CategoryDTO categoryDTO) {

        Category category =
                Category.builder().updateTime(LocalDateTime.now())
                        .updateUser(BaseContext.getCurrentId())
                        .createTime(LocalDateTime.now())
                        .createUser(BaseContext.getCurrentId())
                        .status(0)
                        .build();
        BeanUtils.copyProperties(categoryDTO, category);

        categoryMapper.insert(category);
    }

    @Override
    public void delete(Long id) {
        categoryMapper.delete(id);
    }

    @Override
    public List<Category> getByType(Integer type) {
        List<Category> list= categoryMapper.get(type);
        return list;
    }
}
