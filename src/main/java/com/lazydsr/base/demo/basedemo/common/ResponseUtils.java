package com.lazydsr.base.demo.basedemo.common;

public class ResponseUtils {
    public static <T> Response<T> success() {
        return success(null);
    }

    public static <T> Response<T> success(T data) {
        return getResponse(0, "success", data);
    }

    public static <T> Response<T> fail() {
        return fail("fail");
    }

    public static <T> Response<T> fail(String message) {
        return getResponse(50000, message);
    }

    public static <T> Response<T> getResponse(Integer code, String message) {
        return getResponse(code, message, null, null);
    }

    public static <T> Response<T> getResponse(Integer code, String message, T data) {
        return getResponse(code, message, data, null);
    }

    public static <T> Response<T> getResponse(Integer code, String message, T data, Response.PageInfo pageInfo) {
        return new Response<T>(code, message, data, pageInfo);
    }
}
