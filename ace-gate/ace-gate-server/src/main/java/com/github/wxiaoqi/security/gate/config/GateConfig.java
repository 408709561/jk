

package com.github.wxiaoqi.security.gate.config;

import com.github.wxiaoqi.security.common.xss.XssFilter;
import com.github.wxiaoqi.security.gate.route.RedisRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ace
 * @create 2018/2/12.
 */
@Configuration
public class GateConfig {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    ZuulProperties zuulProperties;

    @Autowired
    ServerProperties server;

    /**
     * xssFilter注册
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        XssFilter xssFilter = new XssFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean(xssFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }

    @Bean
    RedisRouteLocator redisRouteLocator() {
        RedisRouteLocator redisRouteLocator = new RedisRouteLocator(this.server.getServletPrefix(), this.zuulProperties);
        redisRouteLocator.setRedisTemplate(this.redisTemplate);
        return redisRouteLocator;
    }


}
