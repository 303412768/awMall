package com.wen.mall.config.security;

import com.wen.mall.tools.StaticInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    // 在调用方法之前执行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();

        System.out.println("请求路径："+ request.getRequestURI());

        // 将handler强转为HandlerMethod, 前面已经证实这个handler就是HandlerMethod
        HttpSession session = request.getSession();
        String sId = session.getId();
        System.out.println(session.getId());
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 从方法处理器中获取出要调用的方法
        Method method = handlerMethod.getMethod();
        // 获取出方法上的Access注解
        Authority access = (method.getAnnotation(Authority.class));
        if (access == null) {
            // 如果注解为null, 说明不需要拦截, 直接放过
            return true;
        }

        if (access.roles().length > 0) {
            String[] roles = access.roles();
            Set<String> roleSet = new HashSet<>();
            Collections.addAll(roleSet, roles);

            // 这里我为了方便是直接参数传入权限, 在实际操作中应该是从参数中获取用户Id
            // 到数据库权限表中查询用户拥有的权限集合, 与set集合中的权限进行对比完成权限校验
            List<String> roleList = (List<String>) request.getSession().getAttribute(StaticInfo.SESSION_USER_ROLE_KEY);
            if (roleList == null) {
                response.setStatus(403);
                return false;
            }
            for (String role : roleList) {
                if (StringUtils.isNotBlank(role)) {
                    if (roleSet.contains(role)) {
                        // 校验通过返回true, 否则拦截请求
                        return true;
                    }
                }
            }

        }
        // 拦截之后应该返回公共结果, 这里没做处理
        response.setStatus(403);
        return false;
    }

}
