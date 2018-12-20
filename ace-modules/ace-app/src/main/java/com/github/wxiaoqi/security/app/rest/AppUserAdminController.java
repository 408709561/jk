

package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.AppUserBiz;
import com.github.wxiaoqi.security.app.entity.AppUser;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员用户信息管理
 */
@RestController
@RequestMapping("admin/appUser")
@CheckClientToken
@CheckUserToken
public class AppUserAdminController extends BaseController<AppUserBiz,AppUser,Integer> {

}