package com.fillkie.controller.responseDto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TeamPermissionController : readGroupPermissionTeam
 */
@Data
@AllArgsConstructor
public class PermissionsResDto {
    Map<Integer, Boolean> permission;
}
