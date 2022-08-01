package com.fillkie.controller.requestDto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * TeamPermissionController : createTeamGroup
 */
@Data
public class CreateTeamGroupReqDto {
    @NotBlank(message = "TeamId가 없습니다!")
    private String teamId;
    @NotBlank(message = "GroupName이 없습니다!")
    private String groupName;
}
