package com.wen.mall.security;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*@Configuration
@Component
@Order(1)
@WebFilter(filterName = "authFilter",urlPatterns = {"/api/1.0/catalogs/*"})*/
public class SecurityFilter implements  Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //System.out.println("-------------------------------Authority Filter----------------------------------------------");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //System.out.println(req.getRequestURL().toString());
        /*String s=req.getParameter("chen");
        if (StringUtils.isEmpty(s)) {
            res.setStatus(403);
            return;
        }*/
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
