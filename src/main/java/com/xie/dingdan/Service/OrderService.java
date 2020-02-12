package com.xie.dingdan.Service;

import com.xie.dingdan.Dto.Order;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface OrderService {

    Object InsertOneOrder(Order order);

    Object SelectAllOrder(int page,int rows,String username);

    Object SelectOrderByPhoneDate(int page,int rows,String kname,String gname, String date,String zhuangtai,String kusername);

    Object AddOrderAgin(int id);

    Object DeleteOrder(int id);

    Object UpdateOrder(Order order);

    Object SelectOrderByaddman(int page,int rows,String addman);

    Object SelectOrderByZhuangtai(String zhuangtai, String date,String kusername,String sousuoname);

    Object PrintBiaoQian(String kusername);

    Object HouUpdateOrderZhuangtai(Integer[] idzu,String zhuangtai);

    Object Deletewanchengorder(String kusername);

    Object DeleteChooseOrder(Integer[] idarr);

    Object SelectKehuList(String kusername,String kehuname);

    Object SelectKehuOrderList(String kephone,String kusername);

    Object SelectgongList(String kusername,String gongname);

    Object SelectGongOrderList(String gophone,String kusername);

    Object UpdateOrderZhuangtai(Integer idzu[], String zhuangtai);

//    Object SelectOrderByPhoneUser(String phone);

    Object UpdateOrderPrint(String print,int id);
}
