package com.xie.dingdan.Config;

import com.xie.dingdan.interceptor.AuthenticationInterceptor;
import com.xie.dingdan.interceptor.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedHeaders("Content-Type","X-Requested-With","accept,Origin","Access-Control-Request-Method","Access-Control-Request-Headers","token")
//                .allowedMethods("*")
//                .allowedOrigins("*")
//                .allowCredentials(true);
//    }

//    关闭跨域过滤器
    @Bean
    public FilterRegistrationBean registerKeywordHandleFilter () {
        FilterRegistrationBean filterBean = new FilterRegistrationBean();

        CorsFilter corsFilter = new CorsFilter();

        // 设置过滤器名称, 执行顺序, 拦截url
        String filterName = corsFilter.getClass().getSimpleName();
        int order = 1;
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/**");

        filterBean.setFilter(corsFilter);
        filterBean.setName(filterName);
        filterBean.setOrder(order);
        filterBean.setUrlPatterns(urlPatterns);

//        if (log.isDebugEnabled()) {
//            log.debug(String.format("注册过滤器, 过滤器name: %s, 过滤器order: %d, 过滤器过滤url: %s", filterName, order, urlPatterns));
//        }
        return filterBean;
    }

}
