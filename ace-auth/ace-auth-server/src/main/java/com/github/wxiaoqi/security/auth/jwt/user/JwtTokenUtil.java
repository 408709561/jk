

package com.github.wxiaoqi.security.auth.jwt.user;

import com.github.ag.core.util.jwt.IJWTInfo;
import com.github.ag.core.util.jwt.JWTHelper;
import com.github.wxiaoqi.security.auth.configuration.KeyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author ace
 * @version 2017/9/10
 */
@Component
public class JwtTokenUtil {

    public int getExpire() {
        return expire;
    }

    @Value("${jwt.expire}")
    private int expire;

    @Autowired
    private KeyConfiguration keyConfiguration;

    @Autowired
    private JWTHelper jwtHelper;

    public String generateToken(IJWTInfo jwtInfo, Map<String, String> otherInfo,Date expireTime) throws Exception {
        return jwtHelper.generateToken(jwtInfo, keyConfiguration.getUserPriKey(), expireTime, otherInfo);
    }

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        IJWTInfo infoFromToken = jwtHelper.getInfoFromToken(token, keyConfiguration.getUserPubKey());
        return infoFromToken;
    }



}
