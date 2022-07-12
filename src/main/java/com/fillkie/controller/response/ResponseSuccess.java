package com.fillkie.controller.response;

public class ResponseSuccess<T> extends DefaultResponse{

    private T data;

    public ResponseSuccess(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
