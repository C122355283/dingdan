package com.xie.dingdan.Service;

import com.xie.dingdan.Dto.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Service
public interface UserService {

    Object InsertUser(User user);

    Object Login(String username,String password);

    User FindByUsername(String username);

    Date FindKPaydate(String username);

    Object SelectUserInform(String username);

    Object SelectUser(String kusername);

    Object UpdateUser(User user);

    Object DeleteUser(int id);


}
