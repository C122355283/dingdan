package com.xie.dingdan.Mapper;

import com.xie.dingdan.Dto.Maillist;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MaillistMapper {

    //查询通讯录联系人信息
    @Select("select * from maillist where kusername=#{kusername}")
    List<Maillist> s_allkehu(String kusername);

    //查询名字指定联系人
    @Select("select * from maillist where kusername=#{kusername} and name LIKE '%${name}%'")
    List<Maillist> s_kehuwithname(@Param("name") String name,@Param("kusername") String kusername);

    //添加联系人信息
    @Insert("insert into maillist (name,phone,kusername,lianxiang) values (#{name},#{phone},#{kusername},#{lianxiang})")
    int i_maillist(Maillist maillist);

    //删除联系人信息
    @Delete("delete from maillist where id=#{id}")
    int d_maillist(int id);

    //修改联系人信息
    @Update("update maillist set name=#{name},phone=#{phone},lianxiang=#{lianxiang} where id=#{id}")
    int u_maillist(Maillist maillist);

    //模糊查询所有联系人
    @Select("select * from maillist where kusername=#{kusername}")
    List<Maillist> s_allnameorphone(@Param("kusername") String kusername);

        //查询指定电话的联系人
        @Select("select * from maillist where phone = #{phone} and kusername = #{kusername}")
        Maillist s_byphoneKusername(@Param("phone") String phone,@Param("kusername") String kusername);

        //查询指定电话的联系人
        @Select("select * from maillist where phone = #{phone}")
        Maillist s_byphone(@Param("phone") String phone,@Param("kusername") String kusername);

}
