package com.fillkie.controller.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TeamController : readGroupUsersTeam
 */
@Data
@AllArgsConstructor
public class PermissionGroupUsersDto {
    private String userId;
    private String name;
}
