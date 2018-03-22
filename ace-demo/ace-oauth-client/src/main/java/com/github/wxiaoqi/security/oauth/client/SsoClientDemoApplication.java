package com.github.wxiaoqi.security.oauth.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@SpringBootApplication
@Controller
@SessionAttributes("authorizationRequest")
public class SsoClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoClientDemoApplication.class, args);
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
