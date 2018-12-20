/*
 *
 *  *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>
 *
 *  *  AG-Enterprise 企业版源码
 *  *  郑重声明:
 *  *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  *  老A将追究授予人和传播人的法律责任!
 *
 *  *  This program is free software; you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation; either version 2 of the License, or
 *  *  (at your option) any later version.
 *
 *  *  This program is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *
 *  *  You should have received a copy of the GNU General Public License along
 *  *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.github.wxiaoqi.security.gate.filter;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
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
    @Autowired
    private ZuulProperties zuulProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String tenantFlag = ((HttpServletRequest) request).getHeader(RequestHeaderConstants.TENANT_FLAG);
        if(StringUtils.isNotBlank(tenantFlag)) {
            String urlPath = httpServletRequest.getRequestURI();
            urlPath = urlPath.substring(zuulProperties.getPrefix().length() + 1, urlPath.length());
            String realPath = urlPath.substring(urlPath.indexOf("/"), urlPath.length());
            String tenant = urlPath.substring(0, urlPath.indexOf("/"));
            RequestContext ctx = RequestContext.getCurrentContext();
            // 将租户id放置请求头部传递后端
            ctx.addZuulRequestHeader(RequestHeaderConstants.TENANT,tenant);
            BaseContextHandler.set("tenant", tenant);
            chain.doFilter(new UriWrapperRequest((HttpServletRequest) request, zuulProperties.getPrefix() + realPath), response);
        }else{
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    class UriWrapperRequest extends HttpServletRequestWrapper {
        private String uri;

        public UriWrapperRequest(HttpServletRequest request, String uri) {
            super(request);
            this.uri = uri;
        }

        @Override
        public String getRequestURI() {
            return this.uri;
        }

        @Override
        public String getServletPath() {
            return this.uri;
        }
    }
}
