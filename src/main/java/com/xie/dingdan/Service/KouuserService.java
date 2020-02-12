package com.xie.dingdan.Service;

import com.xie.dingdan.Dto.Kouuser;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public interface KouuserService {

    Object Login(String username,String password);

    Object SelectKouuser(String username);

    Object Rest(Kouuser kouuser);

    Object UpdateKouuser(Kouuser kouuser);

    Object UpdatePassword(String username,String newpassword,String yan,String session);

    Object Renew(String username);

    Object RenewOut(String username);

    Object UpdateXufei(String username,String tai);

    Object SelectXufei();

    Object SelectSubonoff(String username);

    Object YanSuccess(String yan, String session,String phone);

    Object UpdateKouuserImg(String kusername,String img);


}
