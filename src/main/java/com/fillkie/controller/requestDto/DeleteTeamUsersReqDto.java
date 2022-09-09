package com.fillkie.controller.requestDto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * TeamPermissionController : deleteUserTeam
 */
@Data
public class DeleteTeamUsersReqDto {
    @NotBlank(message = "userId를 전송해주세요!")
    private String userId;
    @NotBlank(message = "groupId를 전송해주세요!")
    private String groupId;

}