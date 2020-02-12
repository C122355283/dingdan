package com.xie.dingdan.Service;

import com.aliyuncs.exceptions.ClientException;
import com.xie.dingdan.Config.AliyunConfig;
import com.xie.dingdan.Dto.Weixinuser;
import com.xie.dingdan.result.JSONResult;
import org.springframework.stereotype.Service;

@Service
public interface WeixinService {

    Object WXUser(String code);

    Object SelectWXUser(String openid);

    Object UpdateUserPhone(String yan, String session,String phone,String openid,String number);

    Object DeletePhone(int number,String openid);

    Object SelectGongList(String openid);

    Object SelectOrderList(String kusername,String openid);

    Object userInfo(String code, String returnUrl) throws Exception;

    String authorize(String returnUrl);
}
