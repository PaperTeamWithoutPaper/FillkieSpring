package com.fillkie.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * UserService : oauthLogin
 */
@Data
@AllArgsConstructor
public class AccessRefreshDto {
    private String AccessToken;
    private String RefreshToken;
}
