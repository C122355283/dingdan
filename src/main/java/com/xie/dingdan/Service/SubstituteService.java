package com.xie.dingdan.Service;

import com.xie.dingdan.Dto.Substitute;
import org.springframework.stereotype.Service;

@Service
public interface SubstituteService {

    Object InsertSubstitute(Substitute substitute);

    Object DeleteSubstitute(String id);

    Object UpdateSubstitute(Substitute substitute);

//    Object UpdateSubstituteZhuangtai(String zhuangtai,String id);

    Object SelectSubstituteAdress(String openid);

    Object SelectOrderSub(String orderid);

    Object UpdateSubstituteCourier(String courier,String orderid,String gongsi);

}
