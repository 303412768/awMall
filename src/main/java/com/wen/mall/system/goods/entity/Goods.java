package com.wen.mall.system.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wen.mall.config.init.BaseCodeCategory;
import com.wen.mall.config.init.BaseCodeProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author John
 * @since 2018-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_goods")
@ApiModel(value = "Goods对象", description = "")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "主图ID")
    private String mainPicId;

    @ApiModelProperty(value = "快递说明")
    private String postInfo;

    @ApiModelProperty(value = "规格")
    private String specification;

    @ApiModelProperty(value = "商品状态GoodsStatus，1上架，2折扣，3缺货，4下架，")
    private String status;

    @ApiModelProperty(value = "零售价格")
    private BigDecimal retailPrice;

    @ApiModelProperty(value = "代理价格")
    private BigDecimal wholesalePrice;

    private LocalDateTime updateTime;

    private String catalogId;

    @TableField(exist = false)
    private String statusName;

    public String getStatusName() {
        return BaseCodeProperty.getName(BaseCodeCategory.GOODS_STATUS, this.status);
    }

    @ApiModelProperty(value = "商品介绍")
    private String presentation;


}
