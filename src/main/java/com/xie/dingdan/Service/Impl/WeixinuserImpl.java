package com.xie.dingdan.Service.Impl;

import com.xie.dingdan.Dto.Kouuser;
import com.xie.dingdan.Dto.Order;
import com.xie.dingdan.Dto.Weixinuser;
import com.xie.dingdan.Mapper.WeixinuserMapper;
import com.xie.dingdan.Service.WeixinService;
import com.xie.dingdan.Tool.EncryptUtil;
import com.xie.dingdan.result.JSONResult;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Null;
import java.net.URLEncoder;
import java.util.*;

@Service

public class WeixinuserImpl implements WeixinService {
    @Autowired
    WeixinuserMapper weixinuserMapper;


    @Autowired
    private WxMpService wxMpService;

    //微信授权验证
    @Override
    public Object WXUser(String code){
        System.out.printf("标识："+code);
        return code;
    }

    //手机验证
    @Override
    public Object UpdateUserPhone(String yan, String session,String phone,String openid,String number){
        String yan1="";
        try {
            EncryptUtil des = new EncryptUtil(phone,"utf-8");
            yan1 = des.decode(session);
        }catch (Exception e){
            return JSONResult.errorMsg("验证码错误");
        }
        if(yan1.equals(yan)){
            int count = 0;
            if(number.equals("1")){
                System.out.printf("1");
                count = weixinuserMapper.u_userphone1(phone,openid);
            }else if(number.equals("2")){
                System.out.printf("2");
                count = weixinuserMapper.u_userphone2(phone,openid);
            }else if(number.equals("3")){
                System.out.printf("3");
                count = weixinuserMapper.u_userphone3(phone,openid);
            }
            if(count==1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("添加或修改手机号失败");
            }
        }else{
            return JSONResult.errorMsg("验证码错误");
        }
    }

    //删除手机号码
    @Override
    public Object DeletePhone(int number,String openid){
        int count = 0;
        if(number==1){
            count=weixinuserMapper.u_userphone1("",openid);
            if(count==1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("数据操作失败");
            }
        }else if(number==2){
            count=weixinuserMapper.u_userphone2("",openid);
            if(count==1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("数据操作失败");
            }
        }else if(number==3){
            count=weixinuserMapper.u_userphone3("",openid);
            if(count==1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("数据操作失败");
            }
        }else {
            return JSONResult.errorMsg("删除失败");
        }
    }

    //查询有订单供的供应商
    @Override
    public Object SelectGongList(String openid){
        if(openid==null||openid.equals("")){
            JSONResult.errorMsg("用户未登录");
        }
        Weixinuser w1=weixinuserMapper.s_user(openid);
        Map<String,Kouuser> gonglist = new HashMap<>();
        Set<String> koulist = new HashSet<>();
            koulist.addAll(weixinuserMapper.s_kusername(w1.getPhone1()));
            koulist.addAll(weixinuserMapper.s_kusername(w1.getPhone2()));
            koulist.addAll(weixinuserMapper.s_kusername(w1.getPhone3()));
        if(koulist.size()==0){
            return JSONResult.errorMsg("暂无订单");
        }
        String[] addzu=koulist.toArray(new String[koulist.size()]);
        for (int i =0;i<addzu.length;i++){
            gonglist.put(addzu[i],weixinuserMapper.s_dangkouname(addzu[i]));
        }
        return JSONResult.success(gonglist);
    }

    //查询有订单供的供应商
    @Override
    public Object SelectOrderList(String kusername,String openid){
        List<Order> orderList = new ArrayList<>();
        if(openid==null||openid.equals("")){
            JSONResult.errorMsg("用户未登录");
        }
        Weixinuser w1=weixinuserMapper.s_user(openid);
            orderList.addAll(weixinuserMapper.s_wxuserlistbyaddman(w1.getPhone1(),kusername));
            orderList.addAll(weixinuserMapper.s_wxuserlistbyaddman(w1.getPhone2(),kusername));
            orderList.addAll(weixinuserMapper.s_wxuserlistbyaddman(w1.getPhone3(),kusername));
            if(orderList.size()==0){
                return JSONResult.errorMsg("暂无订单");
            }
        return JSONResult.success(orderList);
}

    //查询用户
    @Override
    public Object SelectWXUser(String openid){
        return JSONResult.success(weixinuserMapper.s_user(openid));
    }



    //回调链接
    @Override
    public String authorize(String returnUrl){
        String url = "http://www.jborder.cn/dingdan/Weixin/userInfo";
        String redirectURL = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
//        log.info("【微信网页授权】获取code,redirectURL={}", redirectURL);

        return redirectURL;
    }


    //获取openID并储存
    @Override
    public Object userInfo(String code, String returnUrl) throws Exception {
//        log.info("【微信网页授权】code={}", code);
//        log.info("【微信网页授权】state={}", returnUrl);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
//            log.info("【微信网页授权】{}", e);
            throw new Exception(e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
//        log.info("【微信网页授权】openId={}", openId);
        System.out.println(openId);
        Weixinuser weixinuser =weixinuserMapper.s_user(openId);
        if (weixinuser==null){
            WxMpUser wxMpUser =wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken,"zh_CN");
            Weixinuser weixinuser1 = new Weixinuser();
            weixinuser1.setOpenid(openId);
            weixinuser1.setWeixinname(wxMpUser.getNickname());
            weixinuser1.setImgUrl(wxMpUser.getHeadImgUrl());
            int count= weixinuserMapper.i_user(weixinuser1);
            if(count==1){

                return JSONResult.success(openId);
            }
        }else {
            return JSONResult.success(openId);
//            + "?openid=" + openId
        }
        return JSONResult.errorMsg("登录失败，服务器错误");
    }
}
