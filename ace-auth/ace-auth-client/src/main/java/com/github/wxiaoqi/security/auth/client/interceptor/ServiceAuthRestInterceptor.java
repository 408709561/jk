package com.github.wxiaoqi.security.auth.client.interceptor;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.config.ServiceAuthConfig;
import com.github.wxiaoqi.security.auth.client.jwt.ServiceAuthUtil;
import com.github.wxiaoqi.core.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.common.exception.auth.ClientForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 服务token认证
 *
 * @author ace
 * @version 2017/9/12
 */
@SuppressWarnings("ALL")
public class ServiceAuthRestInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(ServiceAuthRestInterceptor.class);

    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    private List<String> allowedClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 配置该注解，说明不进行服务拦截
        CheckClientToken annotation = handlerMethod.getBeanType().getAnnotation(CheckClientToken.class);
        IgnoreClientToken ignoreClientToken = handlerMethod.getMethodAnnotation(IgnoreClientToken.class);
        if (annotation == null) {
            annotation = handlerMethod.getMethodAnnotation(CheckClientToken.class);
        }
        if (annotation == null || ignoreClientToken != null) {
            return super.preHandle(request, response, handler);
        } else {
            String token = request.getHeader(serviceAuthConfig.getTokenHeader());
            IJWTInfo infoFromToken = serviceAuthUtil.getInfoFromToken(token);
            String uniqueName = infoFromToken.getUniqueName();
            for (String client : serviceAuthUtil.getAllowedClient()) {
                if (client.equals(uniqueName)) {
                    return super.preHandle(request, response, handler);
                }
            }
            throw new ClientForbiddenException("Client is Forbidden!");
        }
    }
}
