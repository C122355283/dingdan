package com.xie.dingdan.Service.Impl;

import com.xie.dingdan.Dto.User;
import com.xie.dingdan.Mapper.UserMapper;
import com.xie.dingdan.Service.UserService;
import com.xie.dingdan.Tool.TokenUtils;
import com.xie.dingdan.result.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class UserImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public Object InsertUser(User user){
        if(user == null){
            return JSONResult.errorMsg("用户不能为空");
        }
        if(userMapper.s_username(user.getUsername())==null||userMapper.s_username(user.getUsername()).equals("")){
            int count = userMapper.i_user(user);
            if(count == 1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("未知错误");
            }
        }else {
            return JSONResult.errorMsg("用户名重复");
        }
    }

    @Override
    public User FindByUsername(String username){
        return userMapper.s_userbyusername(username);
    }

    @Override
    public Date FindKPaydate(String username){
        return userMapper.s_k_paydate(username);
    }

    //登录验证
    @Override
    public Object Login(String username,String password){
        User user1 = userMapper.login(username);
        if(user1==null){
            return JSONResult.errorMsg("该用户不存在");
        }
        if(user1.getUsername().equals(username)&&user1.getPassword().equals(password)){
            Map<String,String> json=new HashMap<>();
            json.put("username",user1.getUsername());
            json.put("kusername",user1.getKusername());
            json.put("level",String.valueOf(user1.getLevel()));
            json.put("token",TokenUtils.getToken(username,password));
            json.put("name",user1.getName());
            return JSONResult.success(json);
        }else if(!user1.getPassword().equals(password)){
            return JSONResult.errorMsg("密码错误");
        }
        else {
            return JSONResult.errorMsg("未知错误");
        }
    }


    //查询用户信息
    public Object SelectUserInform(String username){
        if(username.equals("")||username==null){
            return JSONResult.errorMsg("未知用户");
        }
        User user = userMapper.s_userbyusername(username);
        if(null==user){
            return JSONResult.errorMsg("不存在该用户");
        }else {
            return JSONResult.success(user);
        }
    }

    //查询用户
    public Object SelectUser(String kusername){
        List<User> users = userMapper.s_user(kusername);
        for (int i =0;i<users.size();i++){
            if(users.get(i).getUsername().equals(kusername)){
                users.remove(i);
                break;
            }
        }
        return JSONResult.success(users);
    }

    //修改用户信息
    @Override
    public Object UpdateUser(User user){
        if(user == null){
            return JSONResult.errorMsg("用户不能为空");
        }
        if(userMapper.s_username(user.getUsername())==null||userMapper.s_username(user.getUsername()).equals("")){
            int count = userMapper.u_user(user);
            if(count == 1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("未知错误");
            }
        }else {
            User user1=userMapper.s_username(user.getUsername());
            if(user.getId()==user1.getId()){
                int count = userMapper.u_user(user);
                if(count == 1){
                    return JSONResult.success();
                }else {
                    return JSONResult.errorMsg("未知错误");
                }
            }else {
                return JSONResult.errorMsg("用户名重复");
            }
        }

    }

    //删除用户
    @Override
    public Object DeleteUser(int id){
        if(id == 0){
            return JSONResult.errorMsg("用户id不能为0");
        }
        int count = userMapper.d_user(id);
        if(count == 1){
            return JSONResult.success();
        }else {
            return JSONResult.errorMsg("未知错误");
        }
    }

}
