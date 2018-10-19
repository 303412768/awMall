package com.wen.mall.tools;

import com.wen.mall.security.user.entity.User;

import javax.servlet.http.HttpServletRequest;

public class SessionTool {

    public static User getCurrentUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(StaticInfo.SESSION_USER_KEY);
    }

}
