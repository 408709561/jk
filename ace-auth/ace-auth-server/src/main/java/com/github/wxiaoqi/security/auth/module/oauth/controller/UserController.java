

package com.github.wxiaoqi.security.auth.module.oauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author ace
 * @create 2018/3/19.
 */
@RestController
public class UserController {

    @RequestMapping("/user")
    public Principal userInfo(Principal principal) {
        return principal;
    }
}
