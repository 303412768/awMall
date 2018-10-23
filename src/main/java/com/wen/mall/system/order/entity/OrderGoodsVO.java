package com.wen.mall.system.order.entity;

import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderGoodsVO{

    private String name;
    private BigDecimal price;
    private String uuid;
    private String mainPicId;
    private String specification;
    private Integer quantity;
    private String postInfo;
    private BigDecimal allPrice;
    private String userId;
    private BigDecimal retailPrice;
    private BigDecimal wholesalePrice;
    private Date updateTime;
    private String cartId;

}
