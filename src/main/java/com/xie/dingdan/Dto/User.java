package com.xie.dingdan.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("用户实体类")
public class User {

    private int id;

    @ApiModelProperty(name = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(name = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    private int level;

    @ApiModelProperty(name = "姓名")
    private String name;

    @ApiModelProperty(name = "档口标识")
    @NotBlank(message = "档口标识不能为空")
    private String kusername;

    @ApiModelProperty(name = "档口名称")
    @NotBlank(message = "档口名称不能为空")
    private String dangkouname;

    @ApiModelProperty(name = "用户图片")
    private String userimg;



}
