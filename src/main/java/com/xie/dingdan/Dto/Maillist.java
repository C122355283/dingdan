package com.xie.dingdan.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Maillist {

    private int id;

    @ApiModelProperty(name = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String name;

    @ApiModelProperty(name = "电话")
    @NotBlank(message = "电话不能为空")
    private String phone;

    @ApiModelProperty(name = "联想")
    private String lianxiang;

    @ApiModelProperty(name = "档口标识")
    @NotBlank(message = "档口标识不能为空")
    private String kusername;

}
