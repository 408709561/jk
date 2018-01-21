package com.github.wxiaoqi.security.auth.jwt.client;

import com.github.wxiaoqi.core.util.jwt.IJWTInfo;
import com.github.wxiaoqi.core.util.jwt.JWTHelper;
import com.github.wxiaoqi.security.auth.configuration.KeyConfiguration;
import com.github.wxiaoqi.security.common.exception.auth.ClientTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;

/**
 * Created by ace on 2017/9/10.
 */
@Configuration
public class ClientTokenUtil {
    private Logger logger = LoggerFactory.getLogger(ClientTokenUtil.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${client.expire}")
    private int expire;
    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private KeyConfiguration keyConfiguration;

    public String generateToken(IJWTInfo jwtInfo) throws Exception {
        return jwtHelper.generateToken(jwtInfo, keyConfiguration.getServicePriKey(), expire);
    }

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        IJWTInfo infoFromToken = jwtHelper.getInfoFromToken(token, keyConfiguration.getServicePubKey());
        Date current = infoFromToken.getExpireTime();
        if(new Date().after(current)){
            throw new ClientTokenException("Client token expired!");
        }
        return infoFromToken;
    }

}
