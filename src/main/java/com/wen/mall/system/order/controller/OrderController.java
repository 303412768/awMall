package com.wen.mall.system.order.controller;

import com.wen.mall.config.bean.CodeMsg;
import com.wen.mall.config.bean.Result;
import com.wen.mall.config.security.Authority;
import com.wen.mall.exception.BussinessException;
import com.wen.mall.security.user.entity.User;
import com.wen.mall.system.order.entity.CartToOrderVO;
import com.wen.mall.system.order.service.IOrderService;
import com.wen.mall.tools.StaticInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/1.0/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Authority(roles={"admin","agency","public"})
    @PostMapping("/add")
    public Result save(@RequestBody CartToOrderVO vo, HttpServletRequest request) {
        String orderNo;
        try {
            orderNo = orderService.addOrder(vo, (User) request.getSession().getAttribute(StaticInfo.SESSION_USER_KEY));
        } catch (BussinessException exception) {
            return Result.error(new CodeMsg(0,exception.getMessage()));
        }
        return Result.success(orderNo);
    }
}
