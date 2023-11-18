package com.linli.linlizuzu.common;

import lombok.Data;

/**
 * @author Damumu
 * 通用结果类
 */
@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result() {
    }


    public static<T> Result<T> success(){
        return new Result<T>(Code.SUCCESS.getCode(),"成功",null);
    }

    public static<T> Result<T> success(T data){
        return new Result<T>(Code.SUCCESS.getCode(),"成功",data);
    }

    public static<T> Result<T> success(int code,String message,T data){
        return new Result<T>(code,message,data);
    }

    public static<T> Result<T> success(int code,String message){
        return new Result<T>(code,message,null);
    }

    public static<T> Result<T> failed(){
        return new Result<T>(Code.FAILED.getCode(),"失败",null);
    }

    public static<T> Result<T> failed(int code,String message){
        return new Result<T>(code,message,null);
    }
}
