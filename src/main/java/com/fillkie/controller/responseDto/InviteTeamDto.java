package com.fillkie.controller.responseDto;

import lombok.Data;

@Data
public class InviteTeamDto {

    private String url;

    public InviteTeamDto(String url) {
        this.url = url;
    }
}
