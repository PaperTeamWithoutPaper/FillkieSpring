package com.fillkie.controller.response;

import lombok.Data;
import lombok.Getter;

@Getter
public class ResponseFailRedirect extends DefaultResponse{

    public String uri;

    public ResponseFailRedirect(boolean success, int code, String msg, String uri) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.uri = uri;
    }
}
