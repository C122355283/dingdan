package com.xie.dingdan.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname CorsFilter
 * @Description TODO 跨域过滤配置
 * @Date 2019/5/11 1:13
 * @Created by FYF
 */
@Component
public class CorsFilter implements Filter {

    private Logger log = LoggerFactory.getLogger(CorsFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (log.isDebugEnabled()) {
            log.debug("过滤器初始化");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("开始过滤");
        }
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request  = (HttpServletRequest) servletRequest;
        response.setHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin")); // 解决跨域访问报错
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600"); // 设置过期时间
        response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, token");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP1.1.

        response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. 

        filterChain.doFilter(servletRequest, servletResponse);
        if (log.isDebugEnabled()) {
            log.debug("过滤结束");
        }
    }

    @Override
    public void destroy() {
        if (log.isDebugEnabled()) {
            log.debug("销毁过滤");
        }
    }
}
