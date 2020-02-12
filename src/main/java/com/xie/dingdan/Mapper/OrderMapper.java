package com.xie.dingdan.Mapper;

import com.xie.dingdan.Dto.Order;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

    //添加新订单
    @Insert("insert into orders (orderid,adress,ordertime,kephone,kename,size,pice,gophone,goname,money,zhuangtai,addman,kusername) values " +
            "(#{orderid},#{adress},#{ordertime},#{kephone},#{kename},#{size},#{pice},#{gophone},#{goname},#{money},#{zhuangtai},#{addman},#{kusername})")
    int i_order(Order order);

        @Update("update orders set goname=#{goname} where gophone=#{gophone}")
        int u_goname(@Param("goname") String goname,@Param("gophone") String gophone);

        @Update("update orders set kename=#{kename} where kephone=#{kephone}")
        int u_kename(@Param("kename") String kename,@Param("kephone") String kephone);

    //查询全部订单订单
    @Select("select * from orders where kusername=#{kusername} order by id desc")
    List<Order> s_orders(String kusername);

    //查询一条订单
    @Select("SELECT * FROM orders WHERE id=#{id}")
    Order s_oneorder(int id);

    //查询录单用户的前十订单
    @Select("select * from orders where addman=#{addman} order by id desc")
    List<Order> s_orderbyname(String addman);
//    @Select("select * from orders where kename LIKE '%${kename}%' and ordertime LIKE '%${ordertime}%' order by id desc")
//    List<Order> s_orderbyname(@Param("kename") String kename,@Param("ordertime") String ordertime);

    //查询条件订单
    @Select("select * from orders where kename LIKE '%${kename}%' and goname LIKE '%${goname}%' and (zhuangtai LIKE '%${zhuangtai}%' or fazhuangtai LIKE '%${zhuangtai}%')" +
            "and ordertime LIKE '%${ordertime}%' and kusername=#{kusername} order by id desc")
    List<Order> s_ordersbyphoneandtime(@Param("kename") String kename,@Param("goname") String goname,@Param("ordertime") String ordertime,
                                       @Param("zhuangtai") String zhuangtai,@Param("kusername") String kusername);

    //查询电话条件订单用户
//    @Select("select * from orders where kephone LIKE '%${kephone}%' order by id desc")
//    List<Order> s_ordersbyphoneanduser(@Param("kephone") String kephone);


    //按订单状态查询
    @Select("select * from orders where (zhuangtai=#{zhuangtai} or fazhuangtai=#{zhuangtai}) " +
            "and ordertime LIKE '%${ordertime}%' and kusername=#{kusername} and kename LIKE '%${kehuname}%' order by id desc")
    List<Order> s_orderbyzhuangtai(@Param("zhuangtai") String zhuangtai,@Param("ordertime") String ordertime,
                                   @Param("kusername") String kusername,@Param("kehuname") String kehuname);

    //删除订单
    @Delete("delete from orders where id=#{id}")
    int d_order(int id);

    //打印未到货订单标签
    @Select("select * from orders where zhuangtai='未到货' and ordertime LIKE '%${ordertime}%' and kusername=#{kusername} order by gophone desc")
    List<Order> s_printorder(@Param("ordertime")String ordertime,@Param("kusername") String kusername);

//    //修改订单
//    @Update("update orders set kephone=#{kephone},kename=#{kename},size=#{size},pice=#{pice}" +
//            ",gophone=#{gophone},goname=#{goname},money=#{money} where id=#{id}")
//    int u_order(Order order);

    //修改订单
    @Update("update orders set size=#{size},pice=#{pice},money=#{money} where id=#{id}")
    int u_order(Order order);

    //修改订单状态为已到货
    @Update("update orders set zhuangtai='已到货' where id=#{id}")
    int u_orderyidao(int id);

    //修改订单状态为未到货
    @Update("update orders set zhuangtai='未到货' where id=#{id}")
    int u_orderweidao(int id);

    //修改订单状态为已完成
    @Update("update orders set zhuangtai='已完成' where id=#{id}")
    int u_orderwancheng(int id);

    //修改订单状态为待发货
    @Update("update orders set fazhuangtai='待发货' where orderid=#{id}")
    int u_orderdaifa(String id);

    //修改订单状态为已发货
    @Update("update orders set fazhuangtai='已发货' where orderid=#{id}")
    int u_orderyifa(String id);

    //一键删除已完成订单
    @Delete("delete from orders where zhuangtai='已完成' and kusername=#{kusername}")
    int d_orderwangcheng(String kusername);

    //查询客户当天订单列表
    @Select("select * from orders where (zhuangtai='未到货' or zhuangtai='已到货') and kusername=#{kusername} and kename LIKE '%${kehuname}%' order by id desc")
    List<Order> s_kehu(@Param("kusername") String kusername,@Param("kehuname") String kehuname);

    //查询供应商当天订单列表
    @Select("select * from orders where (zhuangtai='未到货' or zhuangtai='已到货') and kusername=#{kusername} and goname LIKE '%${gongname}%' order by id desc")
    List<Order> s_gong(@Param("kusername") String kusername,@Param("gongname") String gongname);

    //查询客户订单列表
    @Select("select * from orders where kephone=#{kephone} and (zhuangtai='未到货' or zhuangtai='已到货') and kusername=#{kusername} order by zhuangtai desc")
    List<Order> s_kehulist(@Param("kephone") String kephone,@Param("kusername") String kusername);

    //查询供应商订单列表
    @Select("select * from orders where gophone=#{gophone} and (zhuangtai='未到货' or zhuangtai='已到货') and kusername=#{kusername} order by zhuangtai desc")
    List<Order> s_gonglist(@Param("gophone") String gophone,@Param("kusername") String kusername);

    //修改未到货备注
    @Update("update orders set print=#{print} where id=#{id}")
    int u_orderprint(@Param("print") String print,@Param("id") int id);

}
