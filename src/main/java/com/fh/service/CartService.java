package com.fh.service;

import com.fh.model.ProductCart;

import java.util.List;

public interface CartService {
    Integer addCart(Integer id, Integer count);

    List<ProductCart> selectCartAll();

    Integer deleteCart(Integer goodsId);

    List<ProductCart> sumCart();

    void updateProductCartByIsCheck(String ids);
}

