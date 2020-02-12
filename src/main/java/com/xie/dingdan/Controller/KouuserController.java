package com.xie.dingdan.Controller;

import com.xie.dingdan.Dto.Kouuser;
import com.xie.dingdan.Service.KouuserService;
import com.xie.dingdan.annotation.PassToken;
import com.xie.dingdan.annotation.SuperLoginToken;
import com.xie.dingdan.annotation.SuperUserLoginToken;
import com.xie.dingdan.annotation.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("口客户")
@RestController
@RequestMapping("/Kouuser")
@CrossOrigin
public class KouuserController {

    @Autowired
    KouuserService kouuserService;

    @ApiOperation(value = "登录",notes = "")
    @PostMapping("/Login")
    @PassToken
    public Object Login(@RequestParam() String username,@RequestParam() String password){
        return kouuserService.Login(username, password);
    }

    @ApiOperation(value = "查看用户",notes = "")
    @PostMapping("/SelectKouuser")
    @PassToken
    public Object SelectKouuser(@RequestParam() String username){
        return kouuserService.SelectKouuser(username);
    }

    @ApiOperation(value = "注册",notes = "")
    @PostMapping("/Rest")
    @PassToken
    public Object Rest(@Valid @RequestBody Kouuser kouuser){
        return kouuserService.Rest(kouuser);
    }

    @ApiOperation(value = "验证码验证",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yan", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "session", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", dataType = "String", paramType = "query")
    })
    @PassToken
    @PostMapping("/YanSuccess")
    public Object YanSuccess(@RequestParam() String phone,@RequestParam() String yan, @RequestParam() String session) {
        return kouuserService.YanSuccess(yan,session,phone);
    }

    @ApiOperation(value = "更改个人信息",notes = "")
    @PostMapping("/UpdateKouuser")
    @SuperUserLoginToken
    public Object UpdateKouuser(@RequestBody Kouuser kouuser){
        return kouuserService.UpdateKouuser(kouuser);
    }

    @ApiOperation(value = "修改密码",notes = "")
    @PostMapping("/UpdatePassword")
    @SuperUserLoginToken
    public Object UpdatePassword(@RequestParam() String username,@RequestParam() String newpassword,
                                 @RequestParam() String yan, @RequestParam() String session){
        return kouuserService.UpdatePassword(username, newpassword, yan, session);
    }

    @ApiOperation(value = "续费",notes = "")
    @PostMapping("/Renew")
    @SuperLoginToken
    public Object Renew(@RequestParam() String username){
        return kouuserService.Renew(username);
    }

    @ApiOperation(value = "续费取消",notes = "")
    @PostMapping("/RenewOut")
    @SuperLoginToken
    public Object RenewOut(@RequestParam() String username){
        return kouuserService.RenewOut(username);
    }

    @ApiOperation(value = "续费请求",notes = "")
    @PostMapping("/UpdateXufei")
    @PassToken
    public Object UpdateXufei(@RequestParam() String username,@RequestParam() String tai){
        return kouuserService.UpdateXufei(username,tai);
    }

    @ApiOperation(value = "查询续费请求",notes = "")
    @PostMapping("/SelectXufei")
    @SuperLoginToken
    public Object SelectXufei(){
        return kouuserService.SelectXufei();
    }

    @ApiOperation(value = "查询代发开关状态",notes = "")
    @PostMapping("/SelectSubonoff")
    public Object SelectSubonoff(@RequestParam() String username){
        return kouuserService.SelectSubonoff(username);
    }


    @ApiOperation(value = "修改用户头像",notes = "")
    @PostMapping("/UpdateKouuserImg")
    public Object UpdateKouuserImg(@RequestParam() String kusername,@RequestParam() String img){
        return kouuserService.UpdateKouuserImg(kusername,img);
    }

}
