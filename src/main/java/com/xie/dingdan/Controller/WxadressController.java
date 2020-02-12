package com.xie.dingdan.Controller;

import com.xie.dingdan.Dto.Wxadress;
import com.xie.dingdan.Service.WxadressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("地址管理")
@RestController
@RequestMapping("/Wxadress")
@CrossOrigin
public class WxadressController {
    @Autowired
    WxadressService wxadressService;

    @ApiOperation(value = "查询用户地址",notes = "")
    @PostMapping("/SelectAdress")
    public Object SelectWithOpenid(@RequestParam() String openid){
        return wxadressService.SelectWithOpenid(openid);
    }

    @ApiOperation(value = "添加一个用户地址",notes = "")
    @PostMapping("/InsertNewAdress")
    public Object InsertNewAdress(@Valid @RequestBody Wxadress wxadress){
        return wxadressService.InsertNewAdress(wxadress);
    }

    @ApiOperation(value = "修改一个用户地址",notes = "")
    @PostMapping("/UpdateAdress")
    public Object UpdateAdress(@Valid @RequestBody Wxadress wxadress){
        return wxadressService.UpdateAdress(wxadress);
    }

    @ApiOperation(value = "删除一个用户地址",notes = "")
    @PostMapping("/DeleteAdress")
    public Object DeleteAdress(@RequestParam() int id){
        return wxadressService.DeleteAdress(id);
    }



}
