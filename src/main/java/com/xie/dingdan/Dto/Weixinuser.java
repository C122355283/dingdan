package com.xie.dingdan.Dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("微信用户实体类")
public class Weixinuser {

    private int id;

    private String openid;

    private String weixinname;

    private String phone1;

    private String phone2;

    private String phone3;

    private String imgUrl;

}
