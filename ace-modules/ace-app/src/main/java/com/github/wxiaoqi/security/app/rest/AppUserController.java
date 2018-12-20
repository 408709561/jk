/*
 *
 *  *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>
 *
 *  *  AG-Enterprise 企业版源码
 *  *  郑重声明:
 *  *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  *  老A将追究授予人和传播人的法律责任!
 *
 *  *  This program is free software; you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation; either version 2 of the License, or
 *  *  (at your option) any later version.
 *
 *  *  This program is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *
 *  *  You should have received a copy of the GNU General Public License along
 *  *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

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