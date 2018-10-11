package com.wen.mall.security.login.controller;

import com.wen.mall.config.bean.CodeMsg;
import com.wen.mall.config.bean.Result;
import com.wen.mall.security.login.service.SecurityService;
import com.wen.mall.security.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/1.0")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @PostMapping("/login")
    public Result login(String username, String password, HttpServletRequest request) {
        //验证用户名与密码
        User user = securityService.validUser(username, password);
        if (null == user) {
            return Result.error(CodeMsg.USER_NOT_EXSIST);
        }
        //将用户信息放置到Session
        securityService.setUserToSession(user, request);
        return Result.success();
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        securityService.removeSessionInfo(request);
        return Result.success();
    }

}