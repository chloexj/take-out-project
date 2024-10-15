package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetMapper {
    @Select("select count(id) from setmeal where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId);
}
