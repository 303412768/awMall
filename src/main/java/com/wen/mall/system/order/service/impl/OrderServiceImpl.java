package com.wen.mall.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wen.mall.exception.BussinessException;
import com.wen.mall.security.user.entity.User;
import com.wen.mall.system.cart.entity.Cart;
import com.wen.mall.system.cart.service.ICartService;
import com.wen.mall.system.goods.entity.Goods;
import com.wen.mall.system.goods.service.IGoodsService;
import com.wen.mall.system.order.entity.*;
import com.wen.mall.system.order.mapper.OrderMapper;
import com.wen.mall.system.order.service.IAddressService;
import com.wen.mall.system.order.service.IOrderDetailService;
import com.wen.mall.system.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.mall.tools.GeneratorKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author John
 * @since 2018-10-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private ICartService cartService;

    @Autowired
    private ApplicationEventPublisher publisher;


    @Value("${spring.mail.base-image-path}")
    private String baseImagePath;


    @Override
    public String addOrder(CartToOrderVO vo, User user) {
        if (user == null) {
            throw new BussinessException("身份认证错误！");
        }
        if (null == vo.getOrderGoodsVOList()) {
            throw new BussinessException("购物车中无商品！");
        }
        vo.setUserId(user.getUuid());
        Order order = initOrderInfo(vo, user);
        Address address=initAddress(order, vo);
        List<OrderDetail> orderDetails =initOrderDetailAndTotalPrice(order, vo, user);
        save(order);
        deleteCartInfo(user.getUuid());
        OrderMailInfo orderMailInfo = new OrderMailInfo(order,address,orderDetails,baseImagePath);
        publisher.publishEvent(orderMailInfo);
        return order.getOrderNo();
    }

    /**
     * 初始化订单信息
     *
     * @param vo   vo
     * @param user 用户信息
     * @return 订单
     */
    private Order initOrderInfo(CartToOrderVO vo, User user) {
        String orderNo = GeneratorKey.orderNumber();
        Order order = new Order();
        order.setUuid(orderNo);
        order.setOrderNo(orderNo);
        order.setUpdateTime(LocalDateTime.now());
        order.setUserId(user.getUuid());
        order.setOrderStatues("1");
        return order;
    }

    /**
     * 初始化订单详细信息
     *
     * @param order order
     * @param vo    vo
     * @param user  user
     */
    private List<OrderDetail> initOrderDetailAndTotalPrice(Order order, CartToOrderVO vo, User user) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        BigDecimal total = new BigDecimal(0);
        String role = user.getRole();
        for (OrderGoodsVO obj : vo.getOrderGoodsVOList()) {
            BigDecimal price;
            Goods goods = goodsService.getById(obj.getUuid());
            if (null == goods) {
                throw new BussinessException("商品不存才！");
            }
            if (StringUtils.isBlank(role) || "public".equals(role)) {
                price = goods.getRetailPrice();
            } else {
                price = goods.getWholesalePrice();
            }
            total = total.add(price.multiply(new BigDecimal(obj.getQuantity())));
            initOrderDetails(goods,price, order, Long.valueOf(obj.getQuantity().toString()),obj.getMainPicId(), orderDetails);
        }
        order.setTotal(total);
        orderDetailService.saveBatch(orderDetails);
        return orderDetails;
    }

    /**
     * 初始化收件人地址信息
     *
     * @param order
     * @param vo
     * @return
     */
    private Address initAddress(Order order, CartToOrderVO vo) {
        Address address = new Address();
        address.setUuid(order.getUuid());
        address.setAddress(vo.getAddress());
        address.setIdentity(vo.getIdentity());
        address.setName(vo.getName());
        address.setOrderNo(order.getUuid());
        address.setTel(vo.getTel());
        address.setUserId(order.getUserId());
        address.setWxId(vo.getWxId());
        address.setUpdateTime(LocalDateTime.now());
        addressService.save(address);
        return address;
    }

    private void initOrderDetails(Goods goods,BigDecimal salePrice, Order order, Long quantity,String mainPic, List<OrderDetail> orderDetails) {
        OrderDetail detail = new OrderDetail();
        detail.setUuid(GeneratorKey.getKey());
        detail.setGoodsName(goods.getName());
        detail.setGoodsId(goods.getUuid());
        detail.setOrderNo(order.getOrderNo());
        detail.setPostInfo(goods.getPostInfo());
        detail.setSpecification(goods.getSpecification());
        detail.setUpdateTime(LocalDateTime.now());
        detail.setQuantity(quantity);
        detail.setPrice(salePrice);
        detail.setMainPic(mainPic);
        detail.setUserId(order.getUserId());
        orderDetails.add(detail);



    }

    private void deleteCartInfo(String userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        cartService.remove(queryWrapper);
    }


}
