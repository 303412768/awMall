package com.wen.mall.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author John
 * @since 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_address")
@ApiModel(value="Address对象", description="")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    @ApiModelProperty(value = "收件人姓名")
    private String name;

    @ApiModelProperty(value = "收件人地址")
    private String address;

    @ApiModelProperty(value = "收件人手机号码")
    private String tel;

    @ApiModelProperty(value = "身份证号码")
    private String identity;

    @ApiModelProperty(value = "微信账号")
    private String wxId;

    @ApiModelProperty(value = "订单号ID")
    private String orderNo;

    @ApiModelProperty(value = "创建人ID")
    private String userId;

    private LocalDateTime updateTime;


}
