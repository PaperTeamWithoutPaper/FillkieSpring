package com.fillkie.controller.response;

import lombok.Data;

@Data
public class ResponseFail extends DefaultResponse {

    private boolean success;
    private int code;
    private String msg;

    public ResponseFail(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }
}
