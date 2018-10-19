package com.wen.mall.system.cart.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.mall.config.bean.CodeMsg;
import com.wen.mall.config.bean.PageResult;
import com.wen.mall.config.bean.Result;
import com.wen.mall.config.security.Authority;
import com.wen.mall.security.user.entity.User;
import com.wen.mall.system.cart.entity.Cart;
import com.wen.mall.system.cart.service.ICartService;
import com.wen.mall.tools.GeneratorKey;
import com.wen.mall.tools.QueryWrapperTool;
import com.wen.mall.tools.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author John
 * @since 2018-10-19
 */
@RestController
@RequestMapping("/api/1.0/cart")
public class CartController {

    @Autowired
    private ICartService cartService;


    @GetMapping("")
    public PageResult selectPage(@RequestParam(defaultValue = "1") Long pageNo, @RequestParam(defaultValue = "10")Long pageSize, HttpServletRequest request) {
        Page<Cart> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Cart> queryWrapper= new QueryWrapperTool<Cart>().getQueryWrapper(request);
        queryWrapper.orderByDesc("update_time");
        page = (Page<Cart>) cartService.page(page, queryWrapper);
        return PageResult.instance(page);
    }

    @Authority(roles={"admin","agency","public"})
    @GetMapping("/list")
    public Result list(HttpServletRequest request) {
        QueryWrapper<Cart> queryWrapper= new QueryWrapperTool<Cart>().getQueryWrapper(request);
        List<Cart> list=  cartService.list(queryWrapper);
        return Result.success(list);
    }


    @GetMapping("/{uuid}")
    public Result<Cart> detail(@PathVariable String uuid) {
        return Result.success(cartService.getById(uuid));
    }

    @Authority(roles={"admin","agency","public"})
    @PostMapping("/save")
    public Result save(Cart cart,HttpServletRequest request) {
        cart.setUuid(GeneratorKey.getKey());
        //查询当前用户信息
        User user=SessionTool.getCurrentUser(request);
        if (user == null) {
            return Result.error(CodeMsg.PARAMETER_ERROR);
        }
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", cart.getGoodsId());
        queryWrapper.eq("user_id", user.getUuid());
        int count = cartService.count(queryWrapper);
        if (count == 0) {
            cart.setUserId(user.getUuid());
            cart.setUpdateTime(LocalDateTime.now());
            cartService.save(cart);
        }
        return Result.success();
    }

    @Authority(roles={"admin","agency","public"})
    @PostMapping("/update")
    public Result update(Cart cart) {
        cart.setUpdateTime(LocalDateTime.now());
        cartService.updateById(cart);
        return Result.success();
    }

    @Authority(roles={"admin","agency","public"})
    @PostMapping("/delete/{uuid}")
    public Result delete(@PathVariable List uuid) {
        cartService.removeByIds(uuid);
        return Result.success();
    }

}
