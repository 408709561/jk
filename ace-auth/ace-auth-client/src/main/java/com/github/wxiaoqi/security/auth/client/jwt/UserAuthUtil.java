package com.github.wxiaoqi.security.auth.client.jwt;

import com.github.wxiaoqi.core.constants.CommonConstants;
import com.github.wxiaoqi.core.util.jwt.IJWTInfo;
import com.github.wxiaoqi.core.util.jwt.JWTHelper;
import com.github.wxiaoqi.security.auth.client.config.UserAuthConfig;
import com.github.wxiaoqi.security.common.exception.auth.NonLoginException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by ace on 2017/9/15.
 */
@Configuration
public class UserAuthUtil {

    @Autowired
    private UserAuthConfig userAuthConfig;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private JWTHelper jwtHelper;

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            IJWTInfo infoFromToken = jwtHelper.getInfoFromToken(token, userAuthConfig.getPubKeyByte());
            if(redisTemplate.hasKey(CommonConstants.REDIS_USER_TOKEN+infoFromToken.getId()+infoFromToken.getExpireTime().getTime())){
                throw new NonLoginException("User token is invalid!");
            }
            if(new DateTime(infoFromToken.getExpireTime()).plusMinutes(userAuthConfig.getTokenLimitExpire()).isBeforeNow()){
                throw new NonLoginException("User token expired!");
            }
            return infoFromToken;
        } catch (ExpiredJwtException ex) {
            throw new NonLoginException("User token expired!");
        } catch (SignatureException ex) {
            throw new NonLoginException("User token signature error!");
        } catch (IllegalArgumentException ex) {
            throw new NonLoginException("User token is null or empty!");
        }
    }
}
