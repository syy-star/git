package com.fh.controller;

import com.fh.model.ProductCart;
import com.fh.service.CartService;
import com.fh.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("CartController")
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("addCart")
   /* @PostMapping(value = "addCart")
    @ApiOperation(value = "添加购物车",notes = "根据商品ID和数量添加")
    @ApiImplicitParam(paramType = "add",name = "添加商品个数和数量",value = "id",required = true,dataType = "Integer")
    @ApiResponse(code = 404,message = "路径未找到",response = String.class)*/
    public JsonData addCart(@RequestParam("goodsId") Integer id, Integer count){
        Integer type_count=cartService.addCart(id,count);
        return JsonData.getJsonSuccess(type_count);
    }

    @RequestMapping("selectCartAll")
    public JsonData selectCartAll(){
        List<ProductCart> productCartList=cartService.selectCartAll();
        return JsonData.getJsonSuccess(productCartList);
    }

    @RequestMapping("deleteCart")
    public JsonData deleteCart(Integer goodsId){
        Integer goods=cartService.deleteCart(goodsId);
        return JsonData.getJsonSuccess(goods);
    }

    @RequestMapping("sumCart")
    public JsonData sumCart(){
        List<ProductCart> productCartList=cartService.sumCart();
        return  JsonData.getJsonSuccess(productCartList);
    }

    //更新购物车中商品的状态
    @RequestMapping("updateProductCartByIsCheck")
    public JsonData updateProductCartByIsCheck(@Param("ids") String ids){
        cartService.updateProductCartByIsCheck(ids);
        return JsonData.getJsonSuccess("数据更新成功");
    }


}
