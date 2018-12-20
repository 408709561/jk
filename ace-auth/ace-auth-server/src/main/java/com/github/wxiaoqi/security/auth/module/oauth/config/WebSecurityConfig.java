

package com.github.wxiaoqi.security.auth.module.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by ace on 2017/8/11.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/login", "/oauth/authorize")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .authorizeRequests().antMatchers("/static/**", "/favicon.ico", "/webjars/**","/client/**","/v2/api-docs","/generator/build")
                .permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .and()
                .formLogin().loginPage("/login").permitAll();
    }

}