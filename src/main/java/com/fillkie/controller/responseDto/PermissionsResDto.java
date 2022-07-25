package com.fillkie.controller.responseDto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TeamController : readGroupPermissionTeam
 */
@Data
@AllArgsConstructor
public class PermissionsResDto {
    Map<Integer, Boolean> permission;
}
