package com.wen.mall.security.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.mall.config.bean.CodeMsg;
import com.wen.mall.config.bean.PageResult;
import com.wen.mall.config.bean.Result;
import com.wen.mall.config.security.Authority;
import com.wen.mall.security.user.entity.User;
import com.wen.mall.security.user.service.IUserService;
import com.wen.mall.tools.GeneratorKey;
import com.wen.mall.tools.QueryWrapperTool;
import com.wen.mall.tools.SecurityTool;
import com.wen.mall.tools.StaticInfo;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2018-10-11
 */
@RestController
@RequestMapping("/api/1.0/user")
public class UserController {


    @Autowired
    private IUserService userService;

    @Authority(roles={"admin"})
    @GetMapping("")
    public PageResult selectPage(@RequestParam(defaultValue = "1") Long pageNo, @RequestParam(defaultValue = "10")Long pageSize, HttpServletRequest request) {
        Page<User> page = new Page<>(pageNo, pageSize);
        QueryWrapper<User> queryWrapper= new QueryWrapperTool<User>().getQueryWrapper(request);
        queryWrapper.orderByDesc("update_time");
        page = (Page<User>) userService.page(page, queryWrapper);
        return PageResult.instance(page);
    }

    @Authority(roles={"admin"})
    @GetMapping("/list")
    public Result list(HttpServletRequest request) {
        QueryWrapper<User> queryWrapper= new QueryWrapperTool<User>().getQueryWrapper(request);
        List<User> list=  userService.list(queryWrapper);
        return Result.success(list);
    }


    @GetMapping("/{uuid}")
    public Result<User> detail(@PathVariable String uuid) {
        return Result.success(userService.getById(uuid));
    }


    @PostMapping("/save")
    public Result save(User user) {
        user.setUuid(GeneratorKey.getKey());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
        return Result.success();
    }

    @Authority(roles={"admin"})
    @PostMapping("/update")
    public Result update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.success();
    }

    @Authority(roles={"admin","public"})
    @PostMapping("/updatePass")
    public Result updatePass(String password,String rePassword,HttpServletRequest request) {
        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(rePassword) && password.equals(rePassword)) {
            User user= (User) request.getSession().getAttribute(StaticInfo.SESSION_USER_ROLE_KEY);
            user.setPassword(SecurityTool.strToMD5(password));
            userService.updateById(user);
        }else{
            return Result.error(CodeMsg.PARAMETER_ERROR);
        }
        return Result.success();
    }

    @Authority(roles={"admin","public"})
    @PostMapping("/updateTel")
    public Result updateTel(String tel,HttpServletRequest request) {
        if (StringUtils.isNotBlank(tel) ) {
            User user= (User) request.getSession().getAttribute(StaticInfo.SESSION_USER_ROLE_KEY);
            user.setTel(tel);
            userService.updateById(user);
        }else{
            return Result.error(CodeMsg.PARAMETER_ERROR);
        }
        return Result.success();
    }

    @Authority(roles={"admin"})
    @PostMapping("/delete/{uuid}")
    public Result delete(@PathVariable List uuid) {
        userService.removeByIds(uuid);
        return Result.success();
    }
}
