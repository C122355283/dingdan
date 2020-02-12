package com.xie.dingdan.Service;

import com.xie.dingdan.Dto.Maillist;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MaillistService {

    Object SelectMaillist(int page,int rows,String kusername);

    Object SelectMaillistbyname(int page,int rows,String name,String kusername);

    Object DaochuMaillist(String kusername);

    Object DaoruMaillist(MultipartFile multipartFile, String kusername);

    Object InsertMaillist(Maillist maillist);

    Object DeteleMaillist(int id);

    Object UpdateMaillist(Maillist maillist);

    Object SelectAllByNameOrPhone(String nameorphone,String kusername);


}
