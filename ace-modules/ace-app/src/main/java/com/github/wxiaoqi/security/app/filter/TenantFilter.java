

package com.github.wxiaoqi.security.app.filter;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 租户路径拦截
 * @author ace
 * @create 2018/2/9.
 */
@Component("atenantFilter")
@WebFilter(filterName = "atenantFilter", urlPatterns = {"/api"})
@Order(-2147483648)
public class TenantFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String tenantId = httpServletRequest.getHeader(RequestHeaderConstants.TENANT);
        if(StringUtils.isNotBlank(tenantId)) {
            BaseContextHandler.setTenantID(tenantId);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
