package com.xie.dingdan.Controller;

import com.xie.dingdan.Dto.Order;
import com.xie.dingdan.Dto.Substitute;
import com.xie.dingdan.Service.SubstituteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("代发")
@CrossOrigin
@RestController
@RequestMapping("/Substitute")
public class SubstituteController {
    @Autowired
    SubstituteService substituteService;

    //添加一个代发
    @ApiOperation(value = "添加一个代发",notes = "通过代发实体类添加一个代发")
    @PostMapping("/InsertSubstitute")
//
    public Object InsertSubstitute(@Valid @RequestBody Substitute substitute){
        return substituteService.InsertSubstitute(substitute);
    }

    //删除一个代发
    @ApiOperation(value = "删除一个代发",notes = "通过代发id删除一个代发")
    @PostMapping("/DeleteSubstitute")
    @ApiImplicitParam(name = "orderid",dataType = "String",paramType = "query")
    public Object DeleteSubstitute(@RequestParam() String orderid){
        return substituteService.DeleteSubstitute(orderid);
    }

    //查询一个客户的历史代发地址
    @ApiOperation(value = "查询一个客户的历史代发地址",notes = "查询一个客户的历史代发地址")
    @PostMapping("/SelectSubstituteAdress")
    public Object SelectSubstituteAdress(@RequestParam String openid){
        return substituteService.SelectSubstituteAdress(openid);
    }

    //修改一个代发
    @ApiOperation(value = "添加一个代发",notes = "通过代发实体类添加一个代发")
    @PostMapping("/UpdateSubstitute")
//
    public Object UpdateSubstitute(@Valid @RequestBody Substitute substitute){
        return substituteService.UpdateSubstitute(substitute);
    }

    //查询一个订单代发信息
    @ApiOperation(value = "查询一个订单代发信息",notes = "查询一个订单代发信息")
    @PostMapping("/SelectOrderSub")
    public Object SelectOrderSub(@RequestParam() String orderid){
        return substituteService.SelectOrderSub(orderid);
    }

    //添加代发订单快递单号
    @ApiOperation(value = "添加代发订单快递单号",notes = "添加代发订单快递单号")
    @PostMapping("/UpdateSubstituteCourier")
    public Object UpdateSubstituteCourier(@RequestParam() String courier,
                                   @RequestParam() String orderid,@RequestParam() String gongsi){
        return substituteService.UpdateSubstituteCourier(courier, orderid,gongsi);
    }


}
