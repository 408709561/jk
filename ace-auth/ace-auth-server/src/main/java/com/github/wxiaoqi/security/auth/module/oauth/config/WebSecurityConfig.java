

package com.github.wxiaoqi.security.auth.module.oauth.config;

import com.github.wxiaoqi.security.auth.module.oauth.service.OauthUserDetailsService;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * Created by ace on 2017/8/11.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private OauthUserDetailsService oauthUserDetailsService;

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


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(oauthUserDetailsService).passwordEncoder(new Sha256PasswordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}