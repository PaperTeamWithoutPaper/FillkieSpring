package com.fillkie.controller.requestDto;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TeamPermissionController : updateTeamGroupPermission
 */
@Data
@AllArgsConstructor
public class UpdateTeamGroupPermissionReqDto {
    @NotBlank(message = "groupId가 없습니다")
    private String groupId;
    @NotBlank(message = "permission이 없습니다")
    private Map<Integer, Boolean> permission;
    @NotBlank(message = "userId가 없습니다")
    private List<String> userIds;
}
