package com.fasterapp.base.arch;

import lombok.Getter;

import java.io.Serializable;

/**
 * Api返回对象
 */
public class ApiResponse implements Serializable {
    public static final String Success = "0000";
    public static final String Error = "9999";
    @Getter private String code = Success; // 两位模块代码 + 两位错误代码
    @Getter private String message;
    @Getter private Object data;

    private ApiResponse(Object data) {
        this.code = Success;
        this.data = data;
    }

    private ApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse setMessage(String msg){
        this.message = msg;
        return this;
    }

    public static ApiResponse success(){
        return new ApiResponse(Success, "操作成功。");
    }

    public static ApiResponse success(Object data){
        return new ApiResponse(data);
    }

    public static ApiResponse success(String message, Object data){
        ApiResponse  response = new ApiResponse(Success, message);
        response.data = data;
        return response;
    }

    public static ApiResponse error(){
        return new ApiResponse(Error, "系统异常，请稍后再试。");
    }

    public static ApiResponse error(String msg){
        return new ApiResponse(Error, msg);
    }

    public static ApiResponse error(String errorCode, String msg){
        return new ApiResponse(errorCode, msg);
    }

    public static ApiResponse error(String errorCode, String msg, Object data){
        ApiResponse response = new ApiResponse(errorCode, msg);
        response.data = data;
        return response;
    }
}
