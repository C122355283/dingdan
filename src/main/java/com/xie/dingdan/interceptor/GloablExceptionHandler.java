package com.xie.dingdan.interceptor;

import com.xie.dingdan.result.JSONResult;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@ControllerAdvice
public class GloablExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    //登录验证错误异常501
    public Object handleException(RuntimeException e){
        String msg = e.getMessage();
        if(msg==null||msg.equals("")){
            msg="服务器错误";
        }
        if(msg.equals("该账号已到期，请及时续费")){
            return JSONResult.errorMsg(504,msg);
        }
        return JSONResult.errorMsg(501,msg);
    }

    @ResponseBody
    @ExceptionHandler(SQLException.class)
    //数据库错误异常502
    public Object sqlException(SQLException e){
        String msg = e.getMessage();
        if(msg==null||msg.equals("")){
            msg="数据错误";
        }
        return JSONResult.errorMsg(502,msg);
    }

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    //空指针异常错误503
    public Object NullPointerException(NullPointerException e){
        String msg = e.getMessage();
        if(msg==null||msg.equals("")){
            msg="空异常";
        }
        return JSONResult.errorMsg(503,msg);
    }

    @ResponseBody
    @ExceptionHandler(InvalidFormatException.class)
    //表格异常错误504
    public Object InvalidFormatException(InvalidFormatException e){
        String msg = e.getMessage();
        if(msg==null||msg.equals("")){
            msg="你的excel版本目前解析不了,请更换表格重试，请不要使用导出的表格导入";
        }
        return JSONResult.errorMsg(504,msg);
    }

}
