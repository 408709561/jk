package server.com.github.wxiaoqi.security.oauth.config.security.config;

import org.springframework.context.annotation.Configuration;
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
                .authorizeRequests().antMatchers("/static/**", "/favicon.ico", "/webjars/**")
                .permitAll()
                .and()
                .formLogin().loginPage("/login").permitAll();
    }



}