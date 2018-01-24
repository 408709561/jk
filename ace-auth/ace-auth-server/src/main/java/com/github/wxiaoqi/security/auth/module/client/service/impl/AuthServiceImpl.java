package com.github.wxiaoqi.security.auth.module.client.service.impl;

import com.github.ag.core.constants.CommonConstants;
import com.github.ag.core.util.jwt.IJWTInfo;
import com.github.ag.core.util.jwt.JWTInfo;
import com.github.wxiaoqi.security.auth.feign.IUserService;
import com.github.wxiaoqi.security.auth.jwt.user.JwtTokenUtil;
import com.github.wxiaoqi.security.auth.module.client.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author ace
 */
@Service
public class AuthServiceImpl implements AuthService {

    private JwtTokenUtil jwtTokenUtil;
    private IUserService userService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

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
            token = jwtTokenUtil.generateToken(new JWTInfo(data.get("username"), data.get("id"), data.get("name")));
        }
        return token;
    }

    @Override
    public void validate(String token) throws Exception {
        jwtTokenUtil.getInfoFromToken(token);
    }

    @Override
    public Boolean invalid(String token) throws Exception {
        IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(token);
        redisTemplate.opsForValue().set(CommonConstants.REDIS_USER_TOKEN+infoFromToken.getId()+infoFromToken.getExpireTime().getTime(),"1");
        return true;
    }

    @Override
    public String refresh(String oldToken) throws Exception {
        IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(oldToken);
        invalid(oldToken);
        return jwtTokenUtil.generateToken(infoFromToken);
    }
}
