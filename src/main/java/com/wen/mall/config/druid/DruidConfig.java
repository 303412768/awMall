package com.wen.mall.config.druid;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 导入druid数据源，扩展相关的配置属性
 *
 * @author jackson
 */
@Configuration
@EnableTransactionManagement
public class DruidConfig {

    /**
     * 配置Druid的属性，和DataSource进行绑定，前缀设置为：spring.datasource
     * 不配置的话，很多初始化的属性是没有绑定的
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource druid() {
        return new DruidDataSource();
    }

    /**
     * 配置druid监控
     * 配置一个管理后台的servlet
     * 访问地址：http://localhost:8080/druid/
     * @return
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        Map<String, String> initParameters = new HashMap<>(8);
        //属性见：com.alibaba.druid.support.http.ResourceServlet
        initParameters.put("loginUsername", "admin");
        initParameters.put("loginPassword", "123456");
        //默认允许所有
        initParameters.put("allow", "");
        initParameters.put("deny", "");
        bean.setInitParameters(initParameters);
        return bean;
    }

    /**
     * 配置一个web监控的filter
     * @return
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter() {
        FilterRegistrationBean<WebStatFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new WebStatFilter());
        filterBean.setUrlPatterns(Collections.singletonList("/*"));

        Map<String, String> initParameters = new HashMap<>(8);
        //属性见：com.alibaba.druid.support.http.WebStatFilter
        initParameters.put("exclusions", "*.js,*.css,/druid/*");
        filterBean.setInitParameters(initParameters);

        return filterBean;
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }


}
