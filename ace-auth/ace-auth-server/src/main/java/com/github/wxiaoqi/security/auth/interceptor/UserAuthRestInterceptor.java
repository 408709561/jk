package com.github.wxiaoqi.security.auth.interceptor;

import com.github.wxiaoqi.core.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.auth.configuration.UserConfiguration;
import com.github.wxiaoqi.security.auth.jwt.user.JwtTokenUtil;
import com.github.wxiaoqi.core.context.BaseContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ace
 * @version 2017/9/10
 */
public class UserAuthRestInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserConfiguration userConfiguration;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(userConfiguration.getUserTokenHeader());
        IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(token);
        BaseContextHandler.setUsername(infoFromToken.getUniqueName());
        BaseContextHandler.setName(infoFromToken.getName());
        BaseContextHandler.setUserID(infoFromToken.getId());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
