package com.wen.mall.user.service.impl;

import com.wen.mall.user.entity.User;
import com.wen.mall.user.mapper.UserMapper;
import com.wen.mall.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author John
 * @since 2018-09-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
