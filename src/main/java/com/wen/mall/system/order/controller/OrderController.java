package com.wen.mall.system.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wen.mall.config.bean.CodeMsg;
import com.wen.mall.config.bean.Result;
import com.wen.mall.config.security.Authority;
import com.wen.mall.exception.BussinessException;
import com.wen.mall.security.user.entity.User;
import com.wen.mall.system.order.entity.Address;
import com.wen.mall.system.order.entity.CartToOrderVO;
import com.wen.mall.system.order.entity.Order;
import com.wen.mall.system.order.entity.OrderDetail;
import com.wen.mall.system.order.service.IAddressService;
import com.wen.mall.system.order.service.IOrderDetailService;
import com.wen.mall.system.order.service.IOrderService;
import com.wen.mall.tools.QueryWrapperTool;
import com.wen.mall.tools.SessionTool;
import com.wen.mall.tools.StaticInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/1.0/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private IOrderDetailService orderDetailService;

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

    @Authority(roles={"admin","agency","public"})
    @GetMapping("/list")
    public Result list(HttpServletRequest request) {
        QueryWrapper<Order> queryWrapper = new QueryWrapperTool<Order>().getQueryWrapper(request);
        return Result.success(orderService.list(queryWrapper));
    }

    @Authority(roles={"admin","agency","public"})
    @GetMapping("/{orderNo}")
    public Result orderInfo(@PathVariable String orderNo, HttpServletRequest request) {
        QueryWrapper<Order> queryWrapper = new QueryWrapperTool<Order>().getQueryWrapper(request);
        queryWrapper.eq("order_no", orderNo);
        setAuth(request, queryWrapper);
        return Result.success(orderService.getObj(queryWrapper));
    }

    @Authority(roles={"admin","agency","public"})
    @GetMapping("/{orderNo}/address")
    public Result orderAddressInfo(@PathVariable String orderNo, HttpServletRequest request) {
        QueryWrapper<Address> queryWrapper = new QueryWrapperTool<Address>().getQueryWrapper(request);
        queryWrapper.eq("order_no", orderNo);
        setAuth(request, queryWrapper);
        return Result.success(addressService.getObj(queryWrapper));
    }

    @Authority(roles={"admin","agency","public"})
    @GetMapping("/{orderNo}/details")
    public Result orderDetails(@PathVariable String orderNo, HttpServletRequest request) {
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapperTool<OrderDetail>().getQueryWrapper(request);
        queryWrapper.eq("order_no", orderNo);
        setAuth(request, queryWrapper);
        return Result.success(orderDetailService.list(queryWrapper));
    }


    private void setAuth(HttpServletRequest request,QueryWrapper queryWrapper) {
        User user = SessionTool.getCurrentUser(request);
        if (!user.getRole().contains("admin")) {
            queryWrapper.eq("user_id", user.getUuid());
        }
    }


}
