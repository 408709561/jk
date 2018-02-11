package com.github.wxiaoqi.security.gate.filter;

import com.github.ag.core.context.BaseContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * @author ace
 * @create 2018/2/9.
 */
@Component("atenantFilter")
@WebFilter(filterName = "atenantFilter", urlPatterns = {"/api"})
@Order(-2147483648)
@ConditionalOnProperty(name = "gate.tenant.enabled", matchIfMissing = true)
public class TenantFilter implements Filter {
    @Autowired
    private ZuulProperties zuulProperties;
    @Value("${gate.tenant.enable}")
    private Boolean enableTenant;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(enableTenant) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String urlPath = httpServletRequest.getRequestURI();
            urlPath = urlPath.substring(zuulProperties.getPrefix().length() + 1, urlPath.length());
            String realPath = urlPath.substring(urlPath.indexOf("/"), urlPath.length());
            String tenant = urlPath.substring(0, urlPath.indexOf("/"));
            BaseContextHandler.setTenantID(tenant);
            chain.doFilter(new UriWrapperRequest((HttpServletRequest) request, zuulProperties.getPrefix() + realPath), response);
        }else{
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }

    class UriWrapperRequest extends HttpServletRequestWrapper {
        private String uri;

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request
         * @throws IllegalArgumentException if the request is null
         */
        public UriWrapperRequest(HttpServletRequest request,String uri) {
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
