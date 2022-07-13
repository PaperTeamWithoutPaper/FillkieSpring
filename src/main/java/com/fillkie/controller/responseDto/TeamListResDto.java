package com.fillkie.controller.responseDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TeamController : getListTeam
 */
@Data
@AllArgsConstructor
public class TeamListResDto {
    private List<String> teamIdList;
    private List<String> teamNameList;
}
