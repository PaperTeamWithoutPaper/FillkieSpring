package com.fillkie.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TeamService : getTeamDetail
 */
@Data
@AllArgsConstructor
public class TeamDetailDto {
    private String teamName;
    private Long headcount;
}
