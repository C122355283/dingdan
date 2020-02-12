package com.xie.dingdan.Tool;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xie.dingdan.Dto.User;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class TokenUtils {

    public static String getToken(String username,String password){
        String token="";
        token= JWT.create().withAudience(username)
                .sign(Algorithm.HMAC256(password));
        return token;
    }

}
