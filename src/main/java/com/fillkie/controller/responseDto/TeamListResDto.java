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
    private int idx;
    private String teamId;
    private String thumbnail;
    private String teamName;
}
