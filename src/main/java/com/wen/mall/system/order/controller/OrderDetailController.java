package com.wen.mall.system.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wen.mall.config.bean.Result;
import com.wen.mall.config.security.Authority;
import com.wen.mall.security.user.entity.User;
import com.wen.mall.system.order.entity.Address;
import com.wen.mall.system.order.service.IAddressService;
import com.wen.mall.tools.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author John
 * @since 2018-10-23
 */
@RestController
@RequestMapping("/api/1.0/order/details")
public class OrderDetailController {

    @Autowired
    private IAddressService addressService;

    @Authority(roles={"admin","agency","public"})
    @GetMapping("/lastAddress")
    public Result getLastAddressInfo(HttpServletRequest request) {
        User user=SessionTool.getCurrentUser(request);
        if (null == user) {
            return Result.success(null);
        }
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getUuid());
        queryWrapper.orderByDesc("update_time");
        List<Address> list = addressService.list(queryWrapper);
        if (list == null || list.size()==0) {
            return Result.success(null);
        }else{
            return Result.success(list.get(0));
        }
    }

}
