package com.k210.licj.k210.exception;

import com.k210.licj.k210.response.StatusCode;
import lombok.Getter;

public class ServiceException extends RuntimeException{

    @Getter
    private StatusCode statusCode;


    public ServiceException(StatusCode statusCode) {
        super(statusCode.getMsg());
        this.statusCode=statusCode;
    }
}
