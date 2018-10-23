package com.wen.mall.system.order.service;

import com.wen.mall.security.user.entity.User;
import com.wen.mall.system.order.entity.CartToOrderVO;
import com.wen.mall.system.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author John
 * @since 2018-10-23
 */
public interface IOrderService extends IService<Order> {

    @Transactional(rollbackFor = Exception.class)
    String addOrder(CartToOrderVO vo, User user);
}
