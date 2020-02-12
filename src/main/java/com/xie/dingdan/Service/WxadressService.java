package com.xie.dingdan.Service;

import com.xie.dingdan.Dto.Wxadress;
import org.springframework.stereotype.Service;

@Service
public interface WxadressService {

    Object SelectWithOpenid(String openid);

    Object InsertNewAdress(Wxadress wxadress);

    Object UpdateAdress(Wxadress wxadress);

    Object DeleteAdress(int id);

}
