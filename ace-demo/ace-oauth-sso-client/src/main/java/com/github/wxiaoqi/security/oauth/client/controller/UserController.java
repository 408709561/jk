

package com.github.wxiaoqi.security.oauth.client.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author ace
 * @create 2018/3/22.
 */
@RestController
public class UserController {
    @RequestMapping("/user")
    public Principal getPrincipal(Principal principal){
        return principal;
    }
}
