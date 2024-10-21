package com.k210.licj.k210.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StatusCode {
    SUCCESS(1,"ok"),
    ERROR(500,"The service is abnormal"),
    NOT_LOGIN(1000,"Not logged in"),
    LOGIN_SUCCESS(1001,"Login successful"),
    PASSWORD_ERROR(1002,"Wrong password"),
    USERNAME_ERROR(1003, "The username is incorrect"),
    USERNAME_ALREADY_EXISTS(1004, "The username is occupied"),
    FORBIDDEN_ERROR(1005, "No access permissions"),
    OPERATION_SUCCESS(2001, "The operation was successful"),
    OPERATION_FAILED(2002,"The operation failed"),
    VALIDATE_ERROR(3002),
    NO_LOGIN_AUTH(1006,"No login privileges"),
    NOT_FOUND_ERROR(4001);

    StatusCode(Integer code) {
        this.code = code;
    }
    //自定义内部状态码
    private Integer code;
    //状态码的含义
    private String msg;
}






