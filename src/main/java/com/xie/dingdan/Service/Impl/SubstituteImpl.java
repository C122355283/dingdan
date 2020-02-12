package com.xie.dingdan.Service.Impl;

import com.xie.dingdan.Dto.Substitute;
import com.xie.dingdan.Mapper.OrderMapper;
import com.xie.dingdan.Mapper.SubstituteMapper;
import com.xie.dingdan.Service.SubstituteService;
import com.xie.dingdan.result.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubstituteImpl implements SubstituteService {
    @Autowired
    SubstituteMapper substituteMapper;

    @Autowired
    OrderMapper orderMapper;

    //添加一个代发订单
    @Override
    public Object InsertSubstitute(Substitute substitute){
        if(substitute==null){
            return JSONResult.errorMsg("代发信息不能为空");
        }
        if(substitute.getOrderid()==null||substitute.getOrderid().equals("")){
            return JSONResult.errorMsg("订单id不能为空");
        }
        int count = substituteMapper.i_sub(substitute);
        if(count==1){
            count = orderMapper.u_orderdaifa(substitute.getOrderid());
            if(count==1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("申请代发失败");
            }
        }else {
            return JSONResult.errorMsg("未知错误");
        }
    }

    //删除代发信息
    @Override
    public Object DeleteSubstitute(String orderid){
        if(orderid.equals("")||orderid==null){
            return JSONResult.errorMsg("代发id不能为0");
        }
        int count = substituteMapper.d_sub(orderid);
        if(count==1){
            return JSONResult.success();
        }else {
            return JSONResult.errorMsg("未知错误");
        }
    }

    //修改代发信息
    @Override
    public Object UpdateSubstitute(Substitute substitute){
        if(substitute==null){
            return JSONResult.errorMsg("代发信息不能为空");
        }
        int count = substituteMapper.u_sub(substitute);
        if(count==1){
            return JSONResult.success();
        }else {
            return JSONResult.errorMsg("未知错误");
        }
    }

//    //修改一个订单为代发状态
//    @Override
//    public Object UpdateSubstituteZhuangtai(String zhuangtai,String orderid){
//        if(orderid.equals("")||orderid==null){
//            return JSONResult.errorMsg("代发id不能为0");
//        }
//
//        int count = substituteMapper.u_subzhuangtai(zhuangtai,orderid);
//        if(count==1){
//            if(zhuangtai.equals("已完成")){
//                return JSONResult.errorMsg("订单已完成");
//            }
////            else if()
//            if(zhuangtai.equals("代发货")){
//                orderMapper.u_orderdaifa(orderid);
//            }
//            return JSONResult.success();
//        }else {
//            return JSONResult.errorMsg("未知错误");
//        }
//    }

    //查询客户历史地址
    @Override
    public Object SelectSubstituteAdress(String openid){
        if(openid.equals("")||openid==null){
            return JSONResult.errorMsg("用户信息不能为空");
        }
            return JSONResult.success(substituteMapper.s_adress(openid));
    }

    //查询订单代发信息
    @Override
    public Object SelectOrderSub(String orderid){
        if(orderid.equals("")||orderid==null){
            return JSONResult.errorMsg("订单id不能为空");
        }
        return JSONResult.success(substituteMapper.s_sub(orderid));
    }


    //添加代发订单快递单号
    @Override
    public Object UpdateSubstituteCourier(String courier,String orderid,String gongsi){
        if(orderid.equals("")||orderid==null){
            return JSONResult.errorMsg("订单id不能为空");
        }else if(courier.equals("")||courier==null){
            return JSONResult.errorMsg("快递单号不能为空");
        }else if(gongsi.equals("")){
            return JSONResult.errorMsg("请选择快递公司");
        }
        int count = substituteMapper.u_courier(courier,orderid,gongsi);
        if (count==1){
            orderMapper.u_orderyifa(orderid);
            return JSONResult.success();
        }
        return JSONResult.errorMsg("未知错误");
    }



}
