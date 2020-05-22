package com.baoxinhai.community.dto;

import lombok.Data;

@Data
public class ResultDTO {
    private String message;
    private Integer code;
    public static ResultDTO errorof(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(message);
        resultDTO.setCode(code);
        return resultDTO;
    }
}
