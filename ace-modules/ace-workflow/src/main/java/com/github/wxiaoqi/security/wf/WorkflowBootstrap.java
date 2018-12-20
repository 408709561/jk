


package com.github.wxiaoqi.security.wf;

import com.github.wxiaoqi.security.auth.client.EnableAceAuthClient;
import com.github.wxiaoqi.security.wf.activiti.modeler.JsonpCallbackFilter;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan({"org.activiti.rest.diagram", "com.github.wxiaoqi.security.wf"})
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
		org.activiti.spring.boot.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
})
@EnableSwagger2Doc
@EnableAceAuthClient
@EnableEurekaClient
// 开启熔断监控
@EnableCircuitBreaker
// 开启服务鉴权
@EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign","com.github.wxiaoqi.security.wf.feign"})
public class WorkflowBootstrap extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(WorkflowBootstrap.class, args);
	}

	@Bean
	public JsonpCallbackFilter filter(){
		return new JsonpCallbackFilter();
	}
}
