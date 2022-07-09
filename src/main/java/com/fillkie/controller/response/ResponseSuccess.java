package com.fillkie.controller.response;

public class ResponseSuccess<T> {

    private boolean success;
    private String code;
    private String msg;
    private T data;

    public ResponseSuccess(boolean success, String code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
