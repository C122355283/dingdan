package com.xie.dingdan.Mapper;

import com.xie.dingdan.Dto.User;
import org.apache.ibatis.annotations.*;

import javax.validation.constraints.Max;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {

    //添加用户
    @Insert("insert into user (username,password,name,level,dangkouname,kusername) values " +
            "(#{username},#{password},#{name},#{level},#{dangkouname},#{kusername})")
    int i_user(User user);

    //登录验证
    @Select("select * from user where username=#{username}")
    User login(String username);

    //查询用户
    @Select("select * from user where kusername=#{kusername}")
    List<User> s_user(String kusername);

    //修改用户
    @Update("update user set username=#{username},password=#{password},name=#{name},level=#{level} where id=#{id}")
    int u_user(User user);
        //修改用户密码
        @Update("update user set password=#{password} where username=#{username}")
        int u_userpassword(@Param("username") String username,@Param("password") String password);

    //删除用户
    @Delete("delete from user where id=#{id}")
    int d_user(int id);

    //查询是否重名
    @Select("select username,id from user where username=#{username}")
    User s_username(String username);

    //通过用户名查找用户
    @Select("select * from user where username=#{username}")
    User s_userbyusername(String username);

    //查询该档口的过期时间
    @Select("select paydate from kouuser where username=#{kusername}")
    Date s_k_paydate(String kusername);

}
