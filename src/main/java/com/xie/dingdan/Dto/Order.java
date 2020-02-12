package com.xie.dingdan.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel("订单实体类")
public class Order {

    private int id;

    //订单id
    private String orderid;

    @ApiModelProperty(name = "图片地址")
    private String adress;

    @ApiModelProperty(name = "订单时间")
//    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date ordertime;

    @ApiModelProperty(name = "客户电话")
    @NotBlank(message = "客户电话不能为空")
    private String kephone;

    @ApiModelProperty(name = "客户昵称")
    @NotBlank(message = "客户昵称不能为空")
    private String kename;

    @ApiModelProperty(name = "尺码")
    @NotBlank(message = "尺码不能为空")
    private String size;

    @ApiModelProperty(name = "售价")
    @NotNull(message = "价格不能为空")
    private int pice;

    @ApiModelProperty(name = "供应商电话")
    private String gophone;

    @ApiModelProperty(name = "供应商昵称")
    @NotBlank(message = "供应商昵称不能为空")
    private String goname;

    @ApiModelProperty(name = "进价")
    @NotNull(message = "进价不能为空")
    private int money;

    @ApiModelProperty(name = "订单状态")
    private String zhuangtai;

    @ApiModelProperty(name = "发货状态")
    private String fazhuangtai;

    @ApiModelProperty(name = "添加人")
    private String addman;

    @ApiModelProperty(name = "备注")
    private String print;

    @ApiModelProperty(name = "档口标识")
    @NotBlank(message = "档口标识不能为空")
    private String kusername;



}
