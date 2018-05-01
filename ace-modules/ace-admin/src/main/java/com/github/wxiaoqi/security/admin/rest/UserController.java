/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.github.wxiaoqi.security.admin.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.admin.biz.MenuBiz;
import com.github.wxiaoqi.security.admin.biz.PositionBiz;
import com.github.wxiaoqi.security.admin.biz.UserBiz;
import com.github.wxiaoqi.security.admin.entity.Menu;
import com.github.wxiaoqi.security.admin.entity.Position;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.rpc.service.PermissionService;
import com.github.wxiaoqi.security.admin.vo.AuthUser;
import com.github.wxiaoqi.security.admin.vo.FrontUser;
import com.github.wxiaoqi.security.admin.vo.MenuTree;
import com.github.wxiaoqi.security.api.vo.user.UserInfo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-08 11:51
 */
@RestController
@RequestMapping("user")
@CheckUserToken
@CheckClientToken
public class UserController extends BaseController<UserBiz, User> {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PositionBiz positionBiz;

    @Autowired
    private MenuBiz menuBiz;

    @IgnoreUserToken
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ObjectRestResponse<UserInfo> validate(String username, String password) {
        return new ObjectRestResponse<UserInfo>().data(permissionService.validate(username, password));
    }

    @IgnoreUserToken
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ObjectRestResponse<AuthUser> validate(String username) {
        AuthUser user = new AuthUser();
        BeanUtils.copyProperties(baseBiz.getUserByUsername(username), user);
        return new ObjectRestResponse<AuthUser>().data(user);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ObjectRestResponse<Boolean> changePassword(String oldPass, String newPass) {
        return new ObjectRestResponse<Boolean>().data(baseBiz.changePassword(oldPass, newPass));
    }


    @RequestMapping(value = "/front/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserInfo() throws Exception {
        FrontUser userInfo = permissionService.getUserInfo();
        if (userInfo == null) {
            return ResponseEntity.status(401).body(false);
        } else {
            return ResponseEntity.ok(userInfo);
        }
    }

    @RequestMapping(value = "/front/menus", method = RequestMethod.GET)
    public
    @ResponseBody
    List<MenuTree> getMenusByUsername() throws Exception {
        return permissionService.getMenusByUsername();
    }

    @RequestMapping(value = "/front/menu/all", method = RequestMethod.GET)
    public @ResponseBody
    List<Menu> getAllMenus() throws Exception {
        return menuBiz.selectListAll();
    }

    @RequestMapping(value = "/dataDepart", method = RequestMethod.GET)
    public List<String> getUserDataDepartIds(String userId) {
        if (BaseContextHandler.getUserID().equals(userId)) {
            return baseBiz.getUserDataDepartIds(userId);
        }
        return new ArrayList<>();
    }

    @RequestMapping(value = "/flowPosition", method = RequestMethod.GET)
    public List<String> getUserFlowPositions(String userId) {
        if (BaseContextHandler.getUserID().equals(userId)) {
            return positionBiz.getUserFlowPosition(userId).stream().map(Position::getName).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
