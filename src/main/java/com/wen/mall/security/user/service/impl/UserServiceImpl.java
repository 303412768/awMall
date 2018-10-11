package com.wen.mall.security.user.service.impl;

import com.wen.mall.security.user.entity.User;
import com.wen.mall.security.user.mapper.UserMapper;
import com.wen.mall.security.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author John
 * @since 2018-10-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
