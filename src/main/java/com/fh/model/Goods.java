package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("t_goods")
public class Goods {
    @TableId(value = "goodsId",type = IdType.AUTO)
    private Integer goodsId;
    @TableField("goodsName")
    private String goodsName;
    @TableField("fileName")
    private String fileName;
    @TableField("goodsPrice")
    private Double goodsPrice;
    @TableField("goodsIsUp")
    private Integer goodsIsUp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("productDate")
    private Date productDate;
    @TableField("isNotHotSale")
    private Integer isNotHotSale;
    @TableField("area")
    private String area;
    @TableField("types")
    private String types;
    @TableField("brandId")
    private String brandId;
    @TableField("stockCount")
    private Integer stockCount;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsIsUp() {
        return goodsIsUp;
    }

    public void setGoodsIsUp(Integer goodsIsUp) {
        this.goodsIsUp = goodsIsUp;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public Integer getIsNotHotSale() {
        return isNotHotSale;
    }

    public void setIsNotHotSale(Integer isNotHotSale) {
        this.isNotHotSale = isNotHotSale;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }
}
