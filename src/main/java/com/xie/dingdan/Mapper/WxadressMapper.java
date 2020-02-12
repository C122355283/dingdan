package com.xie.dingdan.Mapper;

import com.xie.dingdan.Dto.Wxadress;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WxadressMapper {

    @Select("select * from wxadress where openid=#{openid}")
    List<Wxadress> s_allwithopenid(String openid);

    @Insert("insert into wxadress (openid,name,phone,sname,sphone,adress) values (#{openid},#{name},#{phone},#{sname},#{sphone},#{adress})")
    int i_newadress(Wxadress wxadress);

    @Update("update wxadress set name=#{name},phone=#{phone},sname=#{sname},sphone=#{sphone},adress=#{adress} where id=#{id}")
    int u_adress(Wxadress wxadress);

    @Delete("delete from wxadress where id=#{id}")
    int d_adress(int id);
}
