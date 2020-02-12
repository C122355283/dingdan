package com.xie.dingdan.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.xie.dingdan.Config.AliyunConfig;
import com.xie.dingdan.Dto.Weixinuser;
import com.xie.dingdan.Service.WeixinService;
import com.xie.dingdan.Tool.EncryptUtil;
import com.xie.dingdan.result.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("微信用户")
@RestController
@RequestMapping("/Weixin")
@CrossOrigin
public class WeixinController {
    @Autowired
    WeixinService weixinService;

    @ApiOperation(value = "微信授权登录",notes = "")
    @PostMapping("/WeixinLogin")
    public Object InsertUser(@RequestParam() String code){
        return weixinService.WXUser(code);
    }

    @ApiOperation(value = "查询个人信息",notes = "")
    @PostMapping("/SelectWXUser")
    public Object SelectWXUser(@RequestParam() String openid){
        return weixinService.SelectWXUser(openid);
    }

    @ApiOperation(value = "发送验证短信",notes = "")
    @ApiImplicitParam(name = "phone", dataType = "String", paramType = "query")
    @PostMapping("/SmsVerification")
    public Object SmsVerification(@RequestParam() String phone) {

        String yan = "";
        try {
            EncryptUtil des = new EncryptUtil(phone,"utf-8");
            yan = AliyunConfig.sendSms(phone);
            System.out.printf(des.encode(yan));
            return JSONResult.success(des.encode(yan));
        }catch (ClientException e) {
            return JSONResult.errorMsg(300,e.getMessage());
        }catch (Exception e){
            System.out.printf(e.getMessage());
            return JSONResult.errorMsg(301,"发送失败");
        }
    }

    @ApiOperation(value = "手机验证",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yan", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "session", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "number", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query")
    })
    @PostMapping("/UpdateUserPhone")
    public Object UpdateUserPhone(@RequestParam() String phone,@RequestParam() String number,
                                  @RequestParam() String yan, @RequestParam() String session,
                                  @RequestParam() String openid) {
        return weixinService.UpdateUserPhone(yan,session,phone,openid,number);
    }

    @ApiOperation(value = "删除手机",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query")
    })
    @PostMapping("/DeletePhone")
    public Object DeletePhone(@RequestParam() int number, @RequestParam() String openid) {
        return weixinService.DeletePhone(number,openid);
    }

    @ApiOperation(value = "查询有订单的供应商",notes = "")
    @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query")
    @PostMapping("/SelectGongList")
    public Object SelectGongList(String openid){
        return weixinService.SelectGongList(openid);
    }


    @ApiOperation(value = "查询单个供应商有的订单",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "kusername", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query")
    })
    @PostMapping("/SelectOrderList")
    public Object SelectOrderList(String kusername,String openid){
        return weixinService.SelectOrderList(kusername, openid);
    }


    @ApiOperation(value = "微信授权登录调用地址",notes = "")
    @ApiImplicitParam(name = "returnuel", dataType = "String", paramType = "query")

    @GetMapping("/authorize")
    public Object authorize(String returnuel){
        return weixinService.authorize(returnuel);
    }


    @ApiOperation(value = "微信授权登录",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "returnuel", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", dataType = "String", paramType = "query")
    })
    @GetMapping("/userInfo")
    public Object userInfo(@RequestParam(name = "state") String returnuel,String code) throws Exception{
        return weixinService.userInfo(code,returnuel);
    }


}
