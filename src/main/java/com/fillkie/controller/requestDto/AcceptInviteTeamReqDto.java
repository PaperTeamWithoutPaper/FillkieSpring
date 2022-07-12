package com.fillkie.controller.requestDto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TeamController : acceptInviteTeam
 */
@Data
public class AcceptInviteTeamReqDto {

    @NotBlank
    private String url;

}
