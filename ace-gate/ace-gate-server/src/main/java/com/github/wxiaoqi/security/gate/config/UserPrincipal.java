

package com.github.wxiaoqi.security.gate.config;

import com.github.wxiaoqi.gate.ratelimit.config.IUserPrincipal;
import com.github.wxiaoqi.security.auth.client.config.UserAuthConfig;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.ag.core.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ace on 2017/9/23.
 */

public class UserPrincipal implements IUserPrincipal {

    @Autowired
    private UserAuthConfig userAuthConfig;
    @Autowired
    private UserAuthUtil userAuthUtil;

    @Override
    public String getName(HttpServletRequest request) {
        IJWTInfo infoFromToken = getJwtInfo(request);
        return infoFromToken == null ? null : infoFromToken.getUniqueName();
    }

    private IJWTInfo getJwtInfo(HttpServletRequest request) {
        IJWTInfo infoFromToken = null;
        try {
            String authToken = request.getHeader(userAuthConfig.getTokenHeader());
            if(StringUtils.isEmpty(authToken)||!authToken.startsWith(RequestHeaderConstants.JWT_TOKEN_TYPE)) {
                infoFromToken = null;
            } else {
                authToken = authToken.substring(RequestHeaderConstants.JWT_TOKEN_TYPE.length(),authToken.length());
                infoFromToken = userAuthUtil.getInfoFromToken(authToken);
            }
        } catch (Exception e) {
            infoFromToken = null;
        }
        return infoFromToken;
    }

}
