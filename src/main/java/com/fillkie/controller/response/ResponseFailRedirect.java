package com.fillkie.controller.response;

import lombok.Data;

@Data
public class ResponseFailRedirect extends DefaultResponse{

    private String uri;

    public ResponseFailRedirect(boolean success, int code, String msg, String uri) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.uri = uri;
    }
}
