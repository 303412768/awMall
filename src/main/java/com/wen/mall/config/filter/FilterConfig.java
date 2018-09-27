package com.wen.mall.config.filter;

import javax.servlet.*;
import com.wen.mall.security.SecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SecurityFilter());
        registration.addUrlPatterns("/api/1.0/catalogs/*");
        registration.setName("securityFilter");
        registration.setOrder(1);
        return registration;
    }

}
