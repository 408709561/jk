package com.github.wxiaoqi.security.gate.controller;

import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.gate.biz.AccountBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ace
 * @version 2018/1/18.
 */
@RestController
@RequestMapping("/account")
@CheckUserToken
public class AccountRest {

    @Autowired
    private AccountBiz accountBiz;


    @RequestMapping("/test")
    public ObjectRestResponse test(){
        accountBiz.test();
        return new ObjectRestResponse();
    }
}
