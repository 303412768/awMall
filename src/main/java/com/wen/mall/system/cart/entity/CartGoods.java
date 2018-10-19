package com.wen.mall.system.cart.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author John
 * @since 2018-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("v_cart_goods")
@ApiModel(value="CartGoods对象", description="VIEW")
public class CartGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    @ApiModelProperty(value = "商品名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "主图ID")
    private String mainPicId;

    @ApiModelProperty(value = "快递说明")
    private String postInfo;

    @ApiModelProperty(value = "商品介绍")
    private String presentation;

    @ApiModelProperty(value = "规格")
    private String specification;

    @ApiModelProperty(value = "零售价格")
    private BigDecimal retailPrice;

    @ApiModelProperty(value = "代理价格")
    private BigDecimal wholesalePrice;

    private LocalDateTime updateTime;

    private String userId;

    private Integer quantity;

    private String cartId;


}
