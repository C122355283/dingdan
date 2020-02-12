package com.xie.dingdan.Mapper;

import com.xie.dingdan.Dto.Substitute;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubstituteMapper {

    //添加代发订单
    @Insert("insert into substitute (orderid,name,phone,shouname,shouphone,adress) " +
            "values (#{orderid},#{name},#{phone},#{shouname},#{shouphone},#{adress})")
    int i_sub(Substitute substitute);

    //删除代发订单
    @Delete("delete from substitute where orderid=#{orderid}")
    int d_sub(String id);

    //修改代发订单
    @Update("update substitute set name=#{name},phone=#{phone},shouname=#{shouname},shouphone=#{shouphone}" +
            ",adress=#{adress} where orderid=#{orderid}")
    int u_sub(Substitute substitute);

    //查询代发信息
    @Select("select * from substitute where orderid=#{orderid}")
    Substitute s_sub(String orderid);

    //添加代发订单快递单号
    @Update("update substitute set courier=#{courier},couriergongsi=#{gongsi} where orderid=#{orderid}")
    int u_courier(@Param("courier") String courier,@Param("orderid") String orderid,@Param("gongsi") String gongsi);
//    //修改代发订单状态
//    @Update("update substitute set zhuangtai=#{zhuangtai} where orderid=#{id}")
//    int u_subzhuangtai(@Param("zhuangtai") String zhuangtai,@Param("id")String id);

    //查询客户地址
    @Select("select * from substitute where openid=#{openid} order by id desc")
    List<Substitute> s_adress(String openid);

//    //查询代发订单
//    @Select("select * from substitute order by id desc")
//    List<Substitute> s_all();



}
