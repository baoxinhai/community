package com.baoxinhai.community.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomizeException extends RuntimeException {
    private  String message;

}
