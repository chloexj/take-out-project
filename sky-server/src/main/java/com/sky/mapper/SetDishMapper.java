package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetDishMapper {

List<Long> getSetIdByDishIds(List<Long> dishIds);
}