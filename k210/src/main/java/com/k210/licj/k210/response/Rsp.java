package com.k210.licj.k210.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rsp<T> extends PageData implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;

    private Rsp<T> addResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    private Rsp<T> addData(T data) {
        this.data = data;
        return this;
    }

    public static Rsp<Object> success() {
        return success(null);
    }

    public static Rsp<Object> success(String msg) {
        return new Rsp<Object>().addData(null).addResultCode(1, msg);
    }

    public static <T> Rsp<T> success(T data) {
        return success("成功", data);
    }

    public static <T> Rsp<T> success(String msg, T data) {
        return new Rsp<T>().addData(data).addResultCode(1, msg);
    }

    public static Rsp<Object> failure() {
        return failure(0, "失败");
    }

    public static Rsp<Object> failure(String msg) {
        return failure(0, msg);
    }

    public static Rsp<Object> failure(int code, String msg) {
        return new Rsp<>().addResultCode(code, msg);
    }

    public static <T> Rsp<T> failure(int code, String msg, T data) {
        return new Rsp<T>().addData(data).addResultCode(code, msg);
    }
}
