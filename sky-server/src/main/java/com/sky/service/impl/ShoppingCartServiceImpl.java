package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMapper setMapper;
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart =new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        //判断加入购物车的商品是否存在
        //如果存在则数量+1
//套餐ID和dish id   如果是dish还要加口味
       List<ShoppingCart> list= shoppingCartMapper.list(shoppingCart);

       if(list!=null&&list.size()>0){
           ShoppingCart cart = list.get(0);
           cart.setNumber(cart.getNumber()+1);
           shoppingCartMapper.updateNumberById(cart);
       }else {
//        如果不存在就插入一条购物车数据
           //要先从菜品表/套餐表中查询shoppingCartDTO中缺少的内容。然后插入到shoppingCart中
           Long dishId = shoppingCartDTO.getDishId();
//           Long setmealId = shoppingCartDTO.getSetmealId();
           if(dishId!=null){
               //本次添加的是菜品
               Dish dish = dishMapper.getById(dishId);
               shoppingCart.setName(dish.getName());
               shoppingCart.setImage(dish.getImage());
               shoppingCart.setAmount(dish.getPrice());


           }else {
               //添加进来的不是菜品就是套餐，所以直接else
               Long setmealId = shoppingCartDTO.getSetmealId();
               Setmeal setmeal = setMapper.getById(setmealId);
               shoppingCart.setName(setmeal.getName());
               shoppingCart.setImage(setmeal.getImage());
               shoppingCart.setAmount(setmeal.getPrice());

           }

           shoppingCart.setNumber(1);
           shoppingCart.setCreateTime(LocalDateTime.now());
           shoppingCartMapper.insert(shoppingCart);
       }



    }

    @Override
    public List<ShoppingCart> showShoppingCart() {
       ShoppingCart shoppingCart= ShoppingCart.builder().userId(BaseContext.getCurrentId()).build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    @Override
    public void cleanShoppingCart() {
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
    }
}
