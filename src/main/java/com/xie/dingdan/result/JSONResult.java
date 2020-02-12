package com.xie.dingdan.result;


/**
 * @Description: 自定义响应数据结构
 *                 门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式
 *                 200：表示成功
 *                 500：表示错误，错误信息在msg字段中
 *                 555：异常抛出信息
 *
 * @Author: F
 * @Date: 2019-01-22 17:13
 */

public class JSONResult {

    //响应业务状态
    private int code;

    //响应信息
    private String msg;

    //响应数据
    private Object data;

    public JSONResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this. data = data;
    }

    public JSONResult(Object data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }


    //成功，且返回数据
    public static JSONResult success(Object data) {
        return new JSONResult(data);
    }

    //成功，但不返回数据
    public static JSONResult success() {
        return new JSONResult(null);
    }

    //错误，错误信息在msg字段中
    public static JSONResult errorMsg(int code, String msg) {
        return new JSONResult(code, msg, null);
    }

    public static JSONResult errorMsg(String msg) {
        return new JSONResult(500, msg, null);
    }

    //异常抛出信息
    public static JSONResult errorException(String msg) {
        return new JSONResult(555, msg, null);
    }

    //通用
    public static JSONResult result(int code, String msg, Object data) {
        return new JSONResult(code, msg, data);
    }






    //一定要有get和set方法，原理不是很懂
    public int getCode (){
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
