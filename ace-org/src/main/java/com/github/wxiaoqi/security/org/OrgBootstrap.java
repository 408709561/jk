package com.github.wxiaoqi.security.org;

import com.github.wxiaoqi.security.auth.client.EnableAceAuthClient;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ace
 * @version 2017/12/26.
 */
@EnableEurekaClient
@SpringBootApplication
// 开启事务
@EnableTransactionManagement
// 开启熔断监控
@EnableCircuitBreaker
// 开启服务鉴权
@EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign","com.github.wxiaoqi.security.org.feign"})
@MapperScan("com.github.wxiaoqi.security.org.mapper")
@EnableAceAuthClient
@EnableSwagger2Doc
public class OrgBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(OrgBootstrap.class).web(true).run(args);    }
}
