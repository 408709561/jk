package com.github.wxiaoqi.security.auth.client.config;

import com.github.wxiaoqi.security.auth.client.interceptor.ServiceFeignInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * Created by ace on 2017/9/12.
 */
public class FeignApplyConfiguration {
    @Bean
    ServiceFeignInterceptor getClientTokenInterceptor(){
        return new ServiceFeignInterceptor();
    }
}
