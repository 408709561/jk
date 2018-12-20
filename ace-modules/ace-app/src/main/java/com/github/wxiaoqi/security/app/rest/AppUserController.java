

package com.github.wxiaoqi.security.app.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.biz.AppUserBiz;
import com.github.wxiaoqi.security.app.entity.AppUser;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appUser")
@CheckClientToken
@CheckUserToken
public class AppUserController {
    @Autowired
    private AppUserBiz appUserBiz;

    /**
     * 注册
     */
    @PostMapping("register")
    @IgnoreUserToken
    public ObjectRestResponse register(String mobile, String password) {
        AppUser userByMobile = this.appUserBiz.getUserByMobile(mobile);
        if(userByMobile!=null){
            throw new BusinessException("手机号已经存在!");
        }
        AppUser appuser = new AppUser();
        appuser.setMobile(mobile);
        appuser.setPassword(password);
        appUserBiz.insertSelective(appuser);
        return ObjectRestResponse.ok(appuser);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("info")
    public ObjectRestResponse info() {
        AppUser userByMobile = appUserBiz.getUserByMobile(BaseContextHandler.getName());
        return ObjectRestResponse.ok(userByMobile);
    }

    /**
     * 修改密码
     */
    @PostMapping("changePassword")
    public ObjectRestResponse changePassword(String newPassword, String oldPassword) {
        return ObjectRestResponse.ok(appUserBiz.changePassword(oldPassword,newPassword));
    }

}