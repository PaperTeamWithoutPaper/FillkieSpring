package com.fillkie.controller.requestDto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * TeamController : createTeam
 */
@Data
public class CreateTeamReqDto {

    @NotBlank(message = "팀명을 다시 입력해주세요!")
    private String teamName;
}
