package com.xie.dingdan.Dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("用户地址实体类")
public class Wxadress {
    private int id;

    private String openid;

    private String name;

    private String phone;

    @NotBlank(message = "收件人姓名不能为空")
    private String sname;

    @NotBlank(message = "收件人电话不能为空")
    private String sphone;

    @NotBlank(message = "收件人地址不能为空")
    private String adress;

}
