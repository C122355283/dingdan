package com.xie.dingdan.Service.Impl;

import com.xie.dingdan.Dto.Wxadress;
import com.xie.dingdan.Mapper.WxadressMapper;
import com.xie.dingdan.Service.WxadressService;
import com.xie.dingdan.result.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxadressImpl implements WxadressService {
    @Autowired
    WxadressMapper wxadressMapper;

    @Override
    public Object SelectWithOpenid(String openid){
        if (openid==null||openid.equals("")){
            return JSONResult.errorMsg("登录信息错误，请重新登录");
        }else {
            return JSONResult.success(wxadressMapper.s_allwithopenid(openid));
        }
    }

    @Override
    public Object InsertNewAdress(Wxadress wxadress){
        if (null==wxadress){
            return JSONResult.errorMsg("地址信息不能为空");
        }else {
            int count=wxadressMapper.i_newadress(wxadress);
            if (count==1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("数据操作错误");
            }
        }
    }

    @Override
    public Object UpdateAdress(Wxadress wxadress){
        if (wxadress==null){
            return JSONResult.errorMsg("地址信息不能为空");
        }else {
            int count=wxadressMapper.u_adress(wxadress);
            if (count==1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("数据操作错误");
            }
        }
    }

    @Override
    public Object DeleteAdress(int id){
        if (id==0){
            return JSONResult.errorMsg("请传入要删除的订单");
        }else {
            int count=wxadressMapper.d_adress(id);
            if (count==1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("数据操作错误");
            }
        }

    }

}
