package com.wen.mall.system.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartToOrderVO extends Address {
    private BigDecimal total;
    private List<OrderGoodsVO> orderGoodsVOList;

}
