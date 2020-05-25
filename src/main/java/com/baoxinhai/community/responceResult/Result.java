package com.baoxinhai.community.responceResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    public static Result errorof(Integer code,String message){
        Result result=new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    public static Result okof(){
        Result result=new Result();
        result.setCode(200);
        result.setMessage("请求成功");
        return result;
    }
    public static <T> Result okof(T t){
        Result result=new Result();
        result.setCode(200);
        result.setMessage("请求成功");
        result.setData(t);
        return result;
    }

}
