package com.fillkie.controller.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TeamController : readDetailTeam
 */
@Data
@AllArgsConstructor
public class TeamDetailResDto {
    private String teamName;
    private int count;
}
