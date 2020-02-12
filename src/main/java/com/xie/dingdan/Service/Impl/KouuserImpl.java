package com.xie.dingdan.Service.Impl;

import com.xie.dingdan.Dto.Kouuser;
import com.xie.dingdan.Dto.User;
import com.xie.dingdan.Mapper.KouuserMapper;
import com.xie.dingdan.Mapper.UserMapper;
import com.xie.dingdan.Service.KouuserService;
import com.xie.dingdan.Tool.EncryptUtil;
import com.xie.dingdan.Tool.FileUtils;
import com.xie.dingdan.result.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class KouuserImpl implements KouuserService {
    @Autowired
    KouuserMapper kouuserMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public Object Login(String username,String password){
        if(username==""||username==null){
            return JSONResult.errorMsg("用户名不能为空");
        }else if(password==""||password==null){
            return JSONResult.errorMsg("密码不能为空");
        }
        Kouuser kouuser=kouuserMapper.login(username);
        if (kouuser==null){
            return JSONResult.errorMsg("不存在该用户");
        }else {
            if(kouuser.getPassword().equals(password)){
                return JSONResult.success(username);
            }else {
                return JSONResult.errorMsg("密码错误");
            }
        }
    }

    @Override
    public Object SelectKouuser(String username){
        if(username==""||username==null){
            return JSONResult.errorMsg("用户名不能为空");
        }
        Kouuser kouuser=kouuserMapper.login(username);
        if (kouuser==null){
            return JSONResult.errorMsg("不存在该用户");
        }else {
            kouuser.setPassword(null);
            return JSONResult.success(kouuser);
        }
    }

    @Override
    public Object Rest(Kouuser kouuser){
        if(kouuser==null){
            return JSONResult.errorMsg("注册内容不能为空");
        }
        if(kouuserMapper.login(kouuser.getUsername())==null){
            if(userMapper.s_username(kouuser.getUsername())==null){
                Date newtime=new Date();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(newtime);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                newtime=calendar.getTime();
                kouuser.setPaydate(newtime);
                int count = kouuserMapper.i_kouuser(kouuser);
                if(count==1){
                    User user = new User();
                    user.setUsername(kouuser.getUsername());
                    user.setPassword(kouuser.getPassword());
                    user.setDangkouname(kouuser.getName());
                    user.setKusername(kouuser.getUsername());
                    user.setLevel(0);
                    user.setName(kouuser.getName());
                    user.setUserimg(kouuser.getKouuserimg());
                    int count1 = userMapper.i_user(user);
                    if(count1==1){
                        return JSONResult.success();
                    }else {
                        return JSONResult.errorMsg("数据1操作错误");
                    }
                }else {
                    return JSONResult.errorMsg("数据操作错误");
                }
            }else {
                return JSONResult.errorMsg("用户名重复");
            }
        }else {
            return JSONResult.errorMsg("用户名重复");
        }
    }

    //修改用户信息
    @Override
    public Object UpdateKouuser(Kouuser kouuser){
        if(kouuser==null){
            return JSONResult.errorMsg("修改内容不能为空");
        }
        if(!kouuserMapper.s_kouname(kouuser.getUsername()).equals(kouuser.getName())){
            if(kouuserMapper.u_alluserKouname(kouuser.getName(),kouuser.getUsername())>0){
                if(kouuserMapper.u_user_name(kouuser.getName(),kouuser.getUsername())<1){
                    return JSONResult.errorMsg("数据操作错误");
                }
            }else {
                return JSONResult.errorMsg("数据操作错误");
            }
        }
        int count = kouuserMapper.u_kouuser(kouuser);
        if(count==1){
            return JSONResult.success();
        }else {
            return JSONResult.errorMsg("数据操作错误");
        }
    }

    @Override
    public Object UpdatePassword(String username,String newpassword,String yan,String session){
        String yan1;
        try {
            EncryptUtil des = new EncryptUtil(username,"utf-8");
            yan1 = des.decode(session);
        }catch (Exception e){
            return JSONResult.errorMsg("验证码错误");
        }
        if(yan1.equals(yan)){
            Kouuser kouuser=kouuserMapper.login(username);
            if (kouuser==null){
                return JSONResult.errorMsg("不存在该用户");
            }else {
                int count=kouuserMapper.u_kouuserpassword(username,newpassword);
                if(count==1){
                    int count1=userMapper.u_userpassword(username,newpassword);
                    if(count1==1){
                        return JSONResult.success();
                    }else {
                        return JSONResult.errorMsg("数据操作错误");
                    }
                }else {
                    return JSONResult.errorMsg("数据操作错误");
                }
            }
        }else{
            return JSONResult.errorMsg("验证码错误");
        }


    }

    //续费
    @Override
    public Object Renew(String username){
        Date oldtime=kouuserMapper.login(username).getPaydate();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(oldtime);
        calendar.add(Calendar.YEAR, 1);
        Date newtime=new Date();
        newtime=calendar.getTime();
        int count = kouuserMapper.u_kouuserrenew(username,newtime);
        if(count==1){
            if(kouuserMapper.u_kouxufei(username,"否")==1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("数据操作错误");
            }
        }else {
            return JSONResult.errorMsg("数据操作错误");
        }
    }
    //续费取消
    @Override
    public Object RenewOut(String username){
            if(kouuserMapper.u_kouxufei(username,"否")==1){
                return JSONResult.success();
            }else {
                return JSONResult.errorMsg("数据操作错误");
            }
    }

    //提交续费请求
    @Override
    public Object UpdateXufei(String username,String tai){
        int count = kouuserMapper.u_kouxufei(username,tai);
        if(count==1){
            return JSONResult.success();
        }else {
            return JSONResult.errorMsg("数据操作错误");
        }
    }

    //查询续费请求
    @Override
    public Object SelectXufei(){
        return JSONResult.success(kouuserMapper.s_xufei_list());
    }

    @Override
    public Object SelectSubonoff(String username){
        return JSONResult.success(kouuserMapper.s_subonoff(username));
    }

    //验证码验证
    @Override
    public Object YanSuccess(String yan, String session,String phone){
        String yan1="";
        try {
            EncryptUtil des = new EncryptUtil(phone,"utf-8");
            yan1 = des.decode(session);
        }catch (Exception e){
            return JSONResult.errorMsg("验证码错误");
        }
        if(yan1.equals(yan)){
                return JSONResult.success();
        }else{
            return JSONResult.errorMsg("验证码错误");
        }
    }

    @Override
    public Object UpdateKouuserImg(String kusername,String img){
        if(kusername==null||img==null){
            return JSONResult.errorMsg("请上传图片");
        }
        if(kouuserMapper.s_kouuserimg(kusername)==""||kouuserMapper.s_kouuserimg(kusername)==null){
            if(kouuserMapper.u_kouuserimg(kusername,img)==1){
                if(kouuserMapper.u_kouuserimg_2(kusername,img)==1){
                    return JSONResult.success();
                }else {
                    return JSONResult.errorMsg("修改头像失败");
                }
            }else {
                return JSONResult.errorMsg("修改头像失败");
            }
        }else {
            FileUtils.deleteFile(kouuserMapper.s_kouuserimg(kusername));
            if(kouuserMapper.u_kouuserimg(kusername,img)==1){
                if(kouuserMapper.u_kouuserimg_2(kusername,img)==1){
                    return JSONResult.success();
                }else {
                    return JSONResult.errorMsg("修改头像失败");
                }
            }else {
                return JSONResult.errorMsg("修改头像失败");
            }
        }
    }


}
