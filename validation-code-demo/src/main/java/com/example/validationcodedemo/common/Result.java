package com.example.validationcodedemo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qzz
 */
public class Result<T> {

    private Integer code;
    private String message;
    private T data;
    private Object extendData;

    @JsonIgnore
    private static final Map EMPTY_MAP = new HashMap();

    public Result() {
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public Integer getCode() {
        return this.code == null ? 0 : this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message != null && this.message.trim().length() >= 1 ? this.message : "";
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getExtendData() {
        return this.extendData;
    }

    public void setExtendData(Object extendData) {
        this.extendData = extendData;
    }

    public static Result success(Object data) {
        return data == null ? success() : new Result(0, "success", data);
    }

    public static Result success() {
        return new Result(0, "success", EMPTY_MAP);
    }

    public static Result fail() {
        return new Result(10910, "请求失败", EMPTY_MAP);
    }

    public static Result fail(Integer code, String message) {
        return new Result(code, message, EMPTY_MAP);
    }

    public static <T> Result fail(Integer code, String message, T data) {
        return new Result(code, message, data);
    }

}
