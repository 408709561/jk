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

package com.github.wxiaoqi.security.auth.module.client.service.impl;

import com.github.ag.core.constants.CommonConstants;
import com.github.ag.core.util.jwt.IJWTInfo;
import com.github.ag.core.util.jwt.JWTInfo;
import com.github.wxiaoqi.security.auth.feign.IUserService;
import com.github.wxiaoqi.security.auth.jwt.user.JwtTokenUtil;
import com.github.wxiaoqi.security.auth.module.client.service.AuthService;
import com.github.wxiaoqi.security.common.exception.auth.UserInvalidException;
import com.github.wxiaoqi.security.common.util.RedisKeyUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ace
 */
@Service
public class AuthServiceImpl implements AuthService {

    private JwtTokenUtil jwtTokenUtil;
    private IUserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public AuthServiceImpl(
            JwtTokenUtil jwtTokenUtil,
            IUserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    public String login(String username, String password) throws Exception {
        Map<String, String> data = userService.validate(username, password).getData();
        String token = "";
        if (!StringUtils.isEmpty(data.get("id"))) {
            Map<String, String> map = new HashMap<>();
            map.put(CommonConstants.JWT_KEY_TENANT_ID, String.valueOf(data.get("tenantId")));
            map.put(CommonConstants.JWT_KEY_DEPART_ID, String.valueOf(data.get("departId")));
            JWTInfo jwtInfo = new JWTInfo(data.get("username"), data.get("id"), data.get("name"));
            Date expireTime = DateTime.now().plusSeconds(jwtTokenUtil.getExpire()).toDate();
            token = jwtTokenUtil.generateToken(jwtInfo, map, expireTime);
            redisTemplate.opsForValue().set(RedisKeyUtil.buildUserAbleKey(data.get("id"), expireTime), "1");
            return token;
        }
        throw new UserInvalidException("用户不存在或账户密码错误!");
    }

    @Override
    public void validate(String token) throws Exception {
        jwtTokenUtil.getInfoFromToken(token);
    }

    @Override
    public Boolean invalid(String token) throws Exception {
        IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(token);
        redisTemplate.delete(RedisKeyUtil.buildUserAbleKey(infoFromToken.getId(), infoFromToken.getExpireTime()));
        redisTemplate.opsForValue().set(RedisKeyUtil.buildUserDisableKey(infoFromToken.getId(), infoFromToken.getExpireTime()), "1");
        return true;
    }

    @Override
    public String refresh(String oldToken) throws Exception {
        IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(oldToken);
        invalid(oldToken);
        Date expireTime = DateTime.now().plusSeconds(jwtTokenUtil.getExpire()).toDate();
        redisTemplate.opsForValue().set(RedisKeyUtil.buildUserAbleKey(infoFromToken.getId(), expireTime), "1");
        return jwtTokenUtil.generateToken(infoFromToken, infoFromToken.getOtherInfo(), expireTime);
    }
}
