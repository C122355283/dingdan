package com.xie.dingdan.Controller;

import com.xie.dingdan.Dto.User;
import com.xie.dingdan.Service.UserService;
import com.xie.dingdan.Tool.TokenUtils;
import com.xie.dingdan.annotation.UserLoginToken;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("用户")
@CrossOrigin
@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserService userService;

//    @Autowired
//    TokenUtils tokenUtils;

    @ApiOperation(value = "添加一个用户",notes = "通过用户实体类添加一个用户")
    @PostMapping("/InsertUser")
    public Object InsertUser(@Valid @RequestBody User user){
        return userService.InsertUser(user);
    }

    @ApiOperation(value = "登录验证",notes = "登录验证")
    @PostMapping("/Login")
    public Object Login(@RequestParam String username,@RequestParam String password){
        return userService.Login(username,password);
    }

    @ApiOperation(value = "查询用户个人信息",notes = "查询用户个人信息")
    @PostMapping("/SelectUserInform")
    @UserLoginToken
    public Object SelectUserInform(@RequestParam String username){
        return userService.SelectUserInform(username);
    }

    @ApiOperation(value = "查询用户",notes = "查询用户")
    @PostMapping("/SelectUser")
    @UserLoginToken
    public Object SelectUser(@RequestParam String kusername){
        return userService.SelectUser(kusername);
    }

    @ApiOperation(value = "修改一个用户",notes = "通过用户实体类修改一个用户")
    @PostMapping("/UpdateUser")
    @UserLoginToken
    public Object UpdateUser(@Valid @RequestBody User user){
        return userService.UpdateUser(user);
    }

    @ApiOperation(value = "删除一个用户",notes = "通过用户id删除一个用户")
    @PostMapping("/DeteleUser")
    @ApiImplicitParam(name = "id", dataType = "int", paramType = "query")
    @UserLoginToken
    public Object DeteleUser(@RequestParam() int id){
        return userService.DeleteUser(id);
    }


}
