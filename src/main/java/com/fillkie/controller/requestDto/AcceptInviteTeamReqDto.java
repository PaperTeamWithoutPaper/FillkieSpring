package com.fillkie.controller.requestDto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * TeamController : acceptInviteTeam
 */
@Data
public class AcceptInviteTeamReqDto {
    @NotBlank(message = "url을 다시 입력해주세요!")
    private String url;
}
