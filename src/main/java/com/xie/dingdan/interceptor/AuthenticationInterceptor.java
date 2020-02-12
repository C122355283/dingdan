package com.xie.dingdan.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xie.dingdan.Dto.Kouuser;
import com.xie.dingdan.Dto.User;
import com.xie.dingdan.Mapper.KouuserMapper;
import com.xie.dingdan.Service.KouuserService;
import com.xie.dingdan.Service.UserService;
import com.xie.dingdan.Tool.TokenUtils;
import com.xie.dingdan.annotation.PassToken;
import com.xie.dingdan.annotation.SuperLoginToken;
import com.xie.dingdan.annotation.SuperUserLoginToken;
import com.xie.dingdan.annotation.UserLoginToken;
import com.xie.dingdan.result.JSONResult;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Autowired
    KouuserMapper kouuserMapper;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null||token.equals("")) {
                    throw new RuntimeException("登录信息错误，请重新登录");
                }
                // 获取 token 中的 username
                String username;
                try {
                    username = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("未登录，请登录后操作");
                }
                User user = userService.FindByUsername(username);
                String yantoken=TokenUtils.getToken(user.getUsername(),user.getPassword());
                if(!token.equals(yantoken)){
                    throw new RuntimeException("密码错误,请重新登录");
                }
                Date date = new Date();
                if(userService.FindKPaydate(user.getKusername()).before(date)){
                    throw new RuntimeException("该账号已到期，请及时续费");
                }
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("验证错误");
                }
                return true;
            }
        }

        //检查有没有需要商家用户权限的注解
        if (method.isAnnotationPresent(SuperUserLoginToken.class)) {
            SuperUserLoginToken superUserLoginToken = method.getAnnotation(SuperUserLoginToken.class);
            if (superUserLoginToken.required()) {
                // 执行认证
                if (token == null||token.equals("")) {
                    throw new RuntimeException("登录信息错误，请重新登录");
                }
                // 获取 token 中的 username
                String username;
                try {
                    username = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("未登录，请登录后操作");
                }
                Kouuser kouuser = kouuserMapper.login(username);
                if (kouuser == null) {
                    throw new RuntimeException("用户不存在,或用户权限不足");
                }
                String yantoken=TokenUtils.getToken(kouuser.getUsername(),kouuser.getPassword());
                if(!token.equals(yantoken)){
                    throw new RuntimeException("密码错误,请重新登录");
                }
                Date date = new Date();
                if(kouuser.getPaydate().before(date)){
                    throw new RuntimeException("该账号已到期，请及时续费");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(kouuser.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("验证错误");
                }
                return true;
            }
        }


        if (method.isAnnotationPresent(SuperLoginToken.class)) {
            SuperLoginToken superLoginToken = method.getAnnotation(SuperLoginToken.class);
            if (superLoginToken.required()) {
                // 执行认证
                if (token == null||token.equals("")) {
                    throw new RuntimeException("登录信息错误，请重新登录");
                }
                // 获取 token 中的 username
                String username;
                try {
                    username = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("未登录，请登录后操作");
                }
                User user = userService.FindByUsername(username);
                if (user == null) {
                    throw new RuntimeException("用户不存在");
                }else if(user.getLevel()!=9){
                    throw new RuntimeException("用户权限不足");
                }
                String yantoken=TokenUtils.getToken(user.getUsername(),user.getPassword());
                if(!token.equals(yantoken)){
                    throw new RuntimeException("密码错误,请重新登录");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("验证错误");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
