package com.xie.dingdan.Controller;

import com.xie.dingdan.Dto.Order;
import com.xie.dingdan.Service.OrderService;
import com.xie.dingdan.annotation.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("订单")
@CrossOrigin
@RestController
@RequestMapping("/Order")
public class OrderController {

    @Autowired
    OrderService orderService;

//后台

    //查询所有订单
    @ApiOperation(value = "查询所有订单",notes = "通过页码页数查询所有订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "rows",dataType = "int",paramType = "query")
    })
    @UserLoginToken
    @PostMapping("/SelectAllOrder")
    public Object SelectAllOrder(@RequestParam() int page,
                                 @RequestParam(defaultValue = "10") int rows,
                                 @RequestParam() String username){
        int pages = 1;
        if(page==0||page==1){
            return orderService.SelectAllOrder(pages,rows,username);
        }else {
            return orderService.SelectAllOrder(page,rows,username);
        }
    }

    //查询录单用户的前十订单
    @ApiOperation(value = "查询录单用户的前十订单",notes = "通过页码页数，昵称订单时间查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "rows",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "addman",dataType = "String",paramType = "query")
    })
    @PostMapping("/SelectOrderByaddman")
    public Object SelectOrderByaddman(@RequestParam() int page,@RequestParam(defaultValue = "10") int rows,
                                      String addman){
        return orderService.SelectOrderByaddman(page, rows, addman);
    }

    //添加一个订单
    @ApiOperation(value = "添加一个订单",notes = "通过订单实体类添加一个订单")
    @PostMapping("/InsertOneOrder")
    @UserLoginToken
    public Object InsertOneOrder(@Valid @RequestBody Order order){
        return orderService.InsertOneOrder(order);
    }

    //再来一单
    @ApiOperation(value = "再来一单",notes = "再来一单")
    @ApiImplicitParam(name = "id",dataType = "int",paramType = "query")
    @PostMapping("/AddOrderAgin")
    @UserLoginToken
    public Object AddOrderAgin(@RequestParam() int id){
        return orderService.AddOrderAgin(id);
    }

    //修改一个订单
    @ApiOperation(value = "修改一个订单",notes = "通过订单实体类添加一个订单")
    @PostMapping("/UpdateOneOrder")
    @UserLoginToken
    public Object UpdateOneOrder(@RequestBody Order order){
        return orderService.UpdateOrder(order);
    }

    //删除一个订单
    @ApiOperation(value = "删除一个订单",notes = "通过id删除一个订单")
    @ApiImplicitParam(name = "id",dataType = "int",paramType = "query")
    @PostMapping("/DeleteOrder")
    @UserLoginToken
    public Object DeleteOder(@RequestParam() int id){
        return orderService.DeleteOrder(id);
    }

    //查询条件订单
    @ApiOperation(value = "查询条件订单",notes = "通过页码页数，电话订单时间查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "rows",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "kname",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "gname",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "date",dataType = "Date",paramType = "query"),
            @ApiImplicitParam(name = "zhuangtai",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "kusername",dataType = "String",paramType = "query")
    })
    @PostMapping("/SelectOrderByPhoneDate")
    public Object SelectOrderByPhoneDate(@RequestParam() int page,@RequestParam(defaultValue = "10") int rows,
                                         String kname,String gname,String date,String zhuangtai,String kusername){
        return orderService.SelectOrderByPhoneDate(page, rows, kname, gname, date,zhuangtai,kusername);
    }

    //
    @ApiOperation(value = "删除多个订单",notes = "通过id删除一个订单")
    @PostMapping("/DeleteChooseOrder")
    @UserLoginToken
    public Object DeleteChooseOrder(@RequestParam(name = "idarr") Integer[] idarr){
        return orderService.DeleteChooseOrder(idarr);
    }

    //一键删除完成订单
    @ApiOperation(value = "一键删除完成订单",notes = "一键删除完成订单")
    @PostMapping("/Deletewanchengorder")
    @UserLoginToken
    public Object Deletewanchengorder(@RequestParam() String kusername){
        return orderService.Deletewanchengorder(kusername);
    }



    //标签打印
    @ApiOperation(value = "标签打印",notes = "标签打印")
    @PostMapping("/PrintBiaoQian")
    @UserLoginToken
    public Object PrintBiaoQian(@RequestParam String kusername){
        return orderService.PrintBiaoQian(kusername);
    }

//前台

    //查询状态条件订单
    @ApiOperation(value = "查询状态条件订单",notes = "通过页码页数，状态订单时间查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zhuangtai",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "kusername",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "date",dataType = "Date",paramType = "query")
    })
    @PostMapping("/SelectOrderByZhuangtai")
    public Object SelectOrderByZhuangtai(String zhuangtai,String date,String kusername,String sousuoname){
        return orderService.SelectOrderByZhuangtai(zhuangtai, date,kusername,sousuoname);
    }

    //查询客户列表
    @ApiOperation(value = "查询客户列表",notes = "查询所有未到货的订单，把客户列表显示出来")
    @PostMapping("/SelectKehuList")
    public Object SelectKehuList(@RequestParam() String kusername,String sousuoname){
        return orderService.SelectKehuList(kusername,sousuoname);
    }

    //客户订单列表查询
    @ApiOperation(value = "客户订单列表查询",notes = "客户订单列表查询")
    @PostMapping("/SelectKehuOrderList")
    public Object SelectKehuOrderList(@RequestParam() String kephone,@RequestParam() String kusername){
        return orderService.SelectKehuOrderList(kephone,kusername);
    }

    //查询供应商列表
    @ApiOperation(value = "查询供应商列表",notes = "查询客户列表")
    @PostMapping("/SelectgongList")
    public Object SelectgongList(@RequestParam() String kusername,String sousuoname){
        return orderService.SelectgongList(kusername,sousuoname);
    }

    //供应商订单列表查询
    @ApiOperation(value = "供应商订单列表查询",notes = "供应商订单列表查询")
    @PostMapping("/SelectGongOrderList")
    public Object SelectGongOrderList(@RequestParam() String gophone,@RequestParam() String kusername){
        return orderService.SelectGongOrderList(gophone,kusername);
    }

    //修改订单状态（已到货需打印）
    @ApiOperation(value = "修改订单状态（已到货需打印）",notes = "修改订单状态（已到货需打印）")
    @PostMapping("/UpdateOrderZhuangtai")
    @UserLoginToken
    public Object UpdateOrderZhuangtai(@RequestParam(name = "idzu") Integer idzu[],
                                       @RequestParam() String zhuangtai){
        return orderService.UpdateOrderZhuangtai(idzu,zhuangtai);
    }

    //修改未到货订单备注
    @ApiOperation(value = "修改未到货订单备注",notes = "修改未到货订单备注")
    @PostMapping("/UpdateOrderPrint")
    @UserLoginToken
    public Object UpdateOrderPrint(@RequestParam() int id,
                                   @RequestParam() String print){
        return orderService.UpdateOrderPrint(print,id);
    }

//微信用户


//所有

    //修改订单状态
    @ApiOperation(value = "后台修改订单状态",notes = "后台修改订单状态")
    @PostMapping("/HouUpdateOrderZhuangtai")
    @UserLoginToken
    public Object HouUpdateOrderZhuangtai(@RequestParam(name = "idzu") Integer[] idzu,
                                          @RequestParam() String zhuangtai){
        return orderService.HouUpdateOrderZhuangtai(idzu,zhuangtai);
    }

}
