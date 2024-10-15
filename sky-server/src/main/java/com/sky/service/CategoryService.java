package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateInfo(CategoryDTO categoryDTO);

    void updateStatus(Integer status, Long id);

    void add(CategoryDTO categoryDTO);

    void delete(Long id);

    List<Category> getByType(Integer type);
}
