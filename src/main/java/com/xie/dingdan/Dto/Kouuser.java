package com.xie.dingdan.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel("客户实体类")
public class Kouuser {

    private int id;

    @ApiModelProperty(name = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(name = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(name = "档口名")
    @NotBlank(message = "档口名不能为空")
    private String name;

    @ApiModelProperty(name = "到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date paydate;

    @ApiModelProperty(name = "代发开关")
    @NotBlank(message = "代发开关不能为空")
    private String subonoff;

    @ApiModelProperty(name = "用户头像")
    private String kouuserimg;

    @ApiModelProperty(name = "是否续费")
    private String xufei;

}
