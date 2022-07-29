package com.fillkie.controller.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TeamPermissionController : readGroupsTeam
 */
@Data
@AllArgsConstructor
public class PermissionGroupsResDto {
    private String groupId;
    private String name;
}
