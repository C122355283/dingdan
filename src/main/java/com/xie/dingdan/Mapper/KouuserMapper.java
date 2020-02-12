package com.xie.dingdan.Mapper;

import com.xie.dingdan.Dto.Kouuser;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;


@Mapper
public interface KouuserMapper {

    //登录
    @Select("select * from kouuser where username=#{#username}")
    Kouuser login(String username);

    //注册
    @Insert("insert into kouuser (username,password,name,paydate,subonoff) values" +
            " (#{username},#{password},#{name},#{paydate},#{subonoff})")
    int i_kouuser(Kouuser kouuser);

    //修改个人信息
    @Update("update kouuser set name=#{name},subonoff=#{subonoff} where username=#{username}")
    int u_kouuser(Kouuser kouuser);

        //查询旧档口名字
        @Select("select name from kouuser where username=#{kouusername}")
        String s_kouname(String kouusername);

        //修改该档口账号下的所有档口名
        @Update("update user set dangkouname=#{name} where kusername=#{kusername}")
        int u_alluserKouname(@Param("name") String name,@Param("kusername") String kusername);

        //修改主账号的名字也为档口名
        @Update("update user set name=#{name} where username=#{kusername}")
        int u_user_name(@Param("name") String name,@Param("kusername") String kusername);

    //修改密码
    @Update("update kouuser set password=#{password} where username=#{username}")
    int u_kouuserpassword(@Param("username")String username,@Param("password")String password);

    //续费
    @Update("update kouuser set paydate=#{paydate} where username=#{username}")
    int u_kouuserrenew(@Param("username")String username,@Param("paydate")Date paydate);

    //续费请求
    @Update("update kouuser set xufei=#{xufei} where username=#{username}")
    int u_kouxufei(@Param("username")String username,@Param("xufei")String xufei);

    //查询续费请求
    @Select("select * from kouuser where xufei='是'")
    List<Kouuser> s_xufei_list();

    //查询代发功能状态
    @Select("select subonoff from kouuser where username=#{#username}")
    String s_subonoff(String username);

    //修改用户头像
    @Update("update kouuser set kouuserimg=#{kouuserimg} where username=#{username}")
    int u_kouuserimg(@Param("username")String kusername,@Param("kouuserimg") String kouuserimg);

        //查询用户旧头像
        @Select("select kouuserimg from kouuser where username=#{username}")
        String s_kouuserimg(String username);

        //改变主账号关系的账号头像
        @Update("update user set userimg=#{img} where username=#{kusername}")
        int u_kouuserimg_2(@Param("kusername") String kusername,@Param("img") String img );
}
