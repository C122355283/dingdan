package com.xie.dingdan.Service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xie.dingdan.Dto.Maillist;
import com.xie.dingdan.Dto.Order;
import com.xie.dingdan.Mapper.MaillistMapper;
import com.xie.dingdan.Mapper.OrderMapper;
import com.xie.dingdan.Service.OrderService;
import com.xie.dingdan.Tool.WordToPInYinUtil;
import com.xie.dingdan.result.JSONResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    MaillistMapper maillistMapper;

    //添加一个订单
    @Override
    public Object InsertOneOrder(Order order) {
        if (order == null) {
            return JSONResult.errorMsg("订单实体类不能为空");
        }
        Date date = new Date();
        order.setOrdertime(date);
        String time = String.valueOf(date);
        String orderid = time.substring(8, 10) + RandomStringUtils.randomAlphanumeric(5);
        order.setOrderid(orderid);
        order.setZhuangtai("未到货");

        Maillist maillist = maillistMapper.s_byphone(order.getKephone(),order.getKusername());
        if (maillist == null) {
            maillist = new Maillist();
            maillist.setName(order.getKename());
            maillist.setPhone(order.getKephone());
            maillist.setKusername(order.getKusername());
            maillist.setLianxiang(order.getKename()+WordToPInYinUtil.ToPinYin(order.getKename())+"/"+WordToPInYinUtil.getPinYinHeadChar(order.getKename()));
            maillistMapper.i_maillist(maillist);
        }else{
            if(order.getKephone().equals(maillist.getPhone())){
                if(!order.getKename().equals(maillist.getName())){
                    maillist.setName(order.getKename());
                    maillist.setLianxiang(order.getKename()+WordToPInYinUtil.ToPinYin(order.getKename())+"/"+WordToPInYinUtil.getPinYinHeadChar(order.getKename()));
                    maillistMapper.u_maillist(maillist);
                    orderMapper.u_kename(order.getKename(),order.getKephone());
                }
            }
        }

        Maillist maillist1 = maillistMapper.s_byphone(order.getGophone(),order.getKusername());
        if (maillist1 == null) {
            maillist1 = new Maillist();
            maillist1.setName(order.getGoname());
            maillist1.setPhone(order.getGophone());
            maillist1.setKusername(order.getKusername());
            maillist1.setLianxiang(order.getGoname()+WordToPInYinUtil.ToPinYin(order.getGoname())+"/"+WordToPInYinUtil.getPinYinHeadChar(order.getGoname()));
            maillistMapper.i_maillist(maillist1);
        }else{
            if(order.getGophone().equals(maillist1.getPhone())){
                if(!order.getGoname().equals(maillist1.getName())){
                    maillist1.setName(order.getGoname());
                    maillist1.setLianxiang(order.getGoname()+WordToPInYinUtil.ToPinYin(order.getGoname())+"/"+WordToPInYinUtil.getPinYinHeadChar(order.getGoname()));
                    maillistMapper.u_maillist(maillist1);
                    orderMapper.u_goname(order.getGoname(),order.getGophone());
                }
            }
        }

        int count = orderMapper.i_order(order);
        if (count == 1) {
            return JSONResult.success();
        } else if (count == 0) {
            return JSONResult.errorMsg("订单添加失败，数据库未能进行操作");
        } else if (count > 1) {
            return JSONResult.errorMsg("订单添加错误，数据库操作有误");
        }
        return JSONResult.errorMsg("未知错误");
    }

    //再来一单
    @Override
    public Object AddOrderAgin(int id){
        Order order = orderMapper.s_oneorder(id);
        if(null==order){
            return JSONResult.errorMsg("未找到该订单，请刷新重试");
        }else {
            return JSONResult.success(order);
        }
    }

    //查询所有订单
    @Override
    public Object SelectAllOrder(int page, int rows,String username) {
        PageHelper.startPage(page, rows);
        return JSONResult.success(PageInfo.of(orderMapper.s_orders(username)));
    }

    //查询录单用户的前十订单
    @Override
    public Object SelectOrderByaddman(int page, int rows, String addman) {
        PageHelper.startPage(page, rows);
        return JSONResult.success(PageInfo.of(orderMapper.s_orderbyname(addman)));
    }

    //查询条件订单
    @Override
    public Object SelectOrderByPhoneDate(int page, int rows, String kname, String gname, String date,String zhuangtai,String kusername) {
        PageHelper.startPage(page, rows);
        return JSONResult.success(PageInfo.of(orderMapper.s_ordersbyphoneandtime(kname,gname,date,zhuangtai,kusername)));
    }

    //查询状态条件订单
    @Override
    public Object SelectOrderByZhuangtai(String zhuangtai, String date,String kusername,String sousuoname) {
//        PageHelper.startPage(page, rows);
        return JSONResult.success(orderMapper.s_orderbyzhuangtai(zhuangtai, date,kusername,sousuoname));
//        PageInfo.of(orderMapper.s_orderbyzhuangtai(zhuangtai, date,kusername))
    }

    //删除订单
    @Override
    public Object DeleteOrder(int id) {
        if (id == 0) {
            return JSONResult.errorMsg("未知订单");
        }
        int count = orderMapper.d_order(id);
        if (count == 1) {
            return JSONResult.success();
        } else {
            return JSONResult.errorMsg("删除失败");
        }

    }


    //删除多个订单
    @Override
    public Object DeleteChooseOrder(Integer[] idarr) {
        if (idarr.length==0) {
            return JSONResult.errorMsg("未知订单");
        }
        int count=0;
        for (int i =0;i<idarr.length;i++){
            count += orderMapper.d_order(idarr[i]);
        }
        if(count==idarr.length) {
            return JSONResult.success("删除成功");
        }
        return JSONResult.errorMsg("未知错误");
    }

    //修改订单
    @Override
    public Object UpdateOrder(Order order) {
        if (order == null) {
            return JSONResult.errorMsg("订单不能为空");
        }
        int count = orderMapper.u_order(order);
        if (count == 1) {
            return JSONResult.success();
        } else {
            return JSONResult.errorMsg("未知错误");
        }
    }

    //后台修改订单状态
    @Override
    public Object HouUpdateOrderZhuangtai(Integer[] idzu,String zhuangtai){
        if(idzu.length==0){
            return JSONResult.errorMsg("请选择需要操作的订单");
        }
        if(zhuangtai.equals("已完成")){
            for(int i =0;i<idzu.length;i++){
                orderMapper.u_orderwancheng(idzu[i]);
            }
            return JSONResult.success();
        }
        return JSONResult.errorMsg("未知错误");
    }

    //一键删除已完成订单
    @Override
    public Object Deletewanchengorder(String kusername){
        int count = orderMapper.d_orderwangcheng(kusername);
        if (count>0){
            return JSONResult.success("删除成功");
        }
        return JSONResult.errorMsg("未知错误");
    }

    @Override
    public Object PrintBiaoQian(String kusername){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ordertime = simpleDateFormat.format(date);
        return JSONResult.success(orderMapper.s_printorder(ordertime,kusername));
    }

    //前台页面
    //查询客户列表
    @Override
    public Object SelectKehuList(String kusername,String kehuname) {
        List<Order> orderList = orderMapper.s_kehu(kusername,kehuname);
        Map<String, Integer> kehulist = new HashMap<>();
        int count = 0;
        for (int i = 0; i < orderList.size(); i++) {
            if (!kehulist.containsKey(orderList.get(i).getKephone()+"/"+orderList.get(i).getKename())) {
                kehulist.put(orderList.get(i).getKephone()+"/"+orderList.get(i).getKename(), 1);
            } else {
                kehulist.put(orderList.get(i).getKephone()+"/"+orderList.get(i).getKename(),
                        kehulist.get(orderList.get(i).getKephone()+"/"+orderList.get(i).getKename()) + 1);
            }
        }
        return JSONResult.success(kehulist);
    }
    //客户订单列表查询
    @Override
    public Object SelectKehuOrderList(String kephone,String kusername){
        List<Order> orderList = orderMapper.s_kehulist(kephone,kusername);
        if(orderList.size()==0){
            return JSONResult.success("暂无订单");
        }else {
            return JSONResult.success(orderList);
        }
    }

    //查询供应商列表
    @Override
    public Object SelectgongList(String kusername,String gongname) {
        List<Order> orderList = orderMapper.s_gong(kusername,gongname);
        Map<String, Integer> gonglist = new HashMap<>();
        int count = 0;
        for (int i = 0; i < orderList.size(); i++) {
            if (!gonglist.containsKey(orderList.get(i).getGophone()+"/"+orderList.get(i).getGoname())) {
                gonglist.put(orderList.get(i).getGophone()+"/"+orderList.get(i).getGoname(), 1);
            } else {
                gonglist.put(orderList.get(i).getGophone()+"/"+orderList.get(i).getGoname(),
                        gonglist.get(orderList.get(i).getGophone()+"/"+orderList.get(i).getGoname()) + 1);
            }
        }
        return JSONResult.success(gonglist);
    }

    //供应商订单列表查询
    @Override
    public Object SelectGongOrderList(String gophone,String kusername){
        List<Order> orderList = orderMapper.s_gonglist(gophone,kusername);
        if(orderList.size()==0){
            return JSONResult.success("暂无订单");
        }else {
            return JSONResult.success(orderList);
        }
    }

    //修改订单状态（已到货需打印）
    @Override
    public Object UpdateOrderZhuangtai(Integer idzu[],String zhuangtai){
        if(idzu.length==0){
            return JSONResult.errorMsg("请选择需要操作的订单");
        }
        if(zhuangtai.equals("已到货")){
            //打印功能还未实现
            for(int i =0;i<idzu.length;i++){
                orderMapper.u_orderyidao(idzu[i]);
            }
            return JSONResult.success();
        }
        else if(zhuangtai.equals("未到货")){
            for(int i =0;i<idzu.length;i++){
                orderMapper.u_orderweidao(idzu[i]);
            }
            return JSONResult.success();
        }
        return JSONResult.errorMsg("未知错误");
    }

    //修改订单备注
    @Override
    public Object UpdateOrderPrint(String print,int id){
        if(id==0){
            return JSONResult.errorMsg("请选择一个订单");
        }else {
            int count=orderMapper.u_orderprint(print,id);
            if(count==1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("服务器错误,请重试");
            }
        }

    }

    //用户
    //查询电话条件订单用户
//    @Override
//    public Object SelectOrderByPhoneUser(String phone) {
//        return JSONResult.success(orderMapper.s_ordersbyphoneanduser(phone));
//    }


}
