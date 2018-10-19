package com.wen.mall.system.cart.service.impl;

import com.wen.mall.system.cart.entity.Cart;
import com.wen.mall.system.cart.mapper.CartMapper;
import com.wen.mall.system.cart.service.ICartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author John
 * @since 2018-10-19
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

}
