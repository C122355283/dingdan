package com.xie.dingdan.Mapper;

import com.xie.dingdan.Dto.Kouuser;
import com.xie.dingdan.Dto.Order;
import com.xie.dingdan.Dto.Weixinuser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WeixinuserMapper {

    @Select("select * from weixinuser where openid=#{openid}")
    Weixinuser s_user(String openid);

    @Insert("insert into weixinuser (openid,weixinname,imgUrl) values (#{openid},#{weixinname},#{imgUrl})")
    int i_user(Weixinuser weixinuser);

    @Update("update weixinuser set phone1=#{phone} where openid=#{openid}")
    int u_userphone1(@Param("phone")String phone,@Param("openid")String openid);

    @Update("update weixinuser set phone2=#{phone} where openid=#{openid}")
    int u_userphone2(@Param("phone")String phone,@Param("openid")String openid);

    @Update("update weixinuser set phone3=#{phone} where openid=#{openid}")
    int u_userphone3(@Param("phone")String phone,@Param("openid")String openid);

    //查询有订单的供应商
    @Select("select kusername from orders where kephone=#{kephone}")
    List<String> s_kusername(String kephone);
        //搜索addman对应的供应商
        @Select("select name,kouuserimg from kouuser where username=#{username}")
        Kouuser s_dangkouname(String username);

    //搜索该商家所有的用户订单
    @Select("select * from orders where kephone=#{kephone} and kusername=#{kusername}")
    List<Order> s_wxuserlistbyaddman(@Param("kephone")String kephone,@Param("kusername")String kusername);
        //搜索供应商下的addman
//        @Select("select username from kouuser where name=#{name}")
//        List<String> s_username(String name);
}
