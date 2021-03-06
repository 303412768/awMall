package com.wen.mall.security.login.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wen.mall.exception.BussinessException;
import com.wen.mall.security.user.entity.User;
import com.wen.mall.security.user.service.IUserService;
import com.wen.mall.tools.GeneratorKey;
import com.wen.mall.tools.SecurityTool;
import com.wen.mall.tools.StaticInfo;
import com.wen.mall.tools.WxHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SecurityService {

    @Autowired
    private IUserService userService;


    public User validUser(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", SecurityTool.strToMD5(password));
        return userService.getOne(queryWrapper);
    }

    public void setUserToSession(User user, HttpServletRequest request) {
        user.setPassword(null);
        HttpSession session = request.getSession();
        session.setAttribute(StaticInfo.SESSION_USER_KEY, user);
        List<String> roleList = new ArrayList<>();
        String role = StringUtils.isBlank(user.getRole()) ? null : user.getRole();
        assert role != null;
        String[] roles = role.split(",");
        Collections.addAll(roleList, roles);
        session.setAttribute(StaticInfo.SESSION_USER_ROLE_KEY, roleList);

    }

    public void removeSessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(StaticInfo.SESSION_USER_KEY);
        session.removeAttribute(StaticInfo.SESSION_USER_ROLE_KEY);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String password = "chenxinhu";
        // 生成一个MD5加密计算摘要
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 计算md5函数
        md.update(password.getBytes());
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
        password = new BigInteger(1, md.digest()).toString(16);
        System.out.println(password);
    }


    public User wxMinLogin(String code , String encryptedData,String iv) {
        String unionid = WxHelper.getUnionIdByMin(encryptedData, iv, code);
        if (StringUtils.isBlank(unionid)) {
            return null;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wx_uuid", unionid);
        User user=userService.getOne(queryWrapper);
        //判断是不是新用户，如果null说明为新用户
        if (null == user) {
            user = new User();
            user.setUuid(GeneratorKey.getKey());
            user.setUsername(user.getUuid());
            user.setPassword(SecurityTool.strToMD5(user.getUuid()));
            user.setUpdateTime(LocalDateTime.now());
            user.setWxUuid(unionid);
            user.setRole("public");
            userService.save(user);
        }
        return user;
    }
}
