package com.fillkie.controller.response;

import lombok.Data;

@Data
public class ResponseFail<T> {
    private boolean success;
    private String code;
    private String msg;
    private T data;

    public ResponseFail(boolean success, String code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
