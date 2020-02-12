package com.xie.dingdan.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("代发实体类")
public class Substitute {

    private int id;

    private String orderid;

    @ApiModelProperty(name = "客户姓名")
    @NotBlank(message = "发件人姓名不能为空")
    private String name;

    @ApiModelProperty(name = "客户电话")
    @NotBlank(message = "发件人电话不能为空")
    private String phone;

    @ApiModelProperty(name = "收货姓名")
    @NotBlank(message = "收件人姓名不能为空")
    private String shouname;

    @ApiModelProperty(name = "收货电话")
    @NotBlank(message = "收件人电话不能为空")
    private String shouphone;

    @ApiModelProperty(name = "信息地址")
    @NotBlank(message = "收件人地址不能为空")
    private String adress;

    @ApiModelProperty(name = "快递单号")
    private String courier;

    @ApiModelProperty(name = "微信账号")
    private String openid;

    @ApiModelProperty(name = "快递公司")
    private String couriergongsi;
}
