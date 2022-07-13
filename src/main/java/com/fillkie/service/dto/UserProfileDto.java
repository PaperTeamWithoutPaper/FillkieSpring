package com.fillkie.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * UserService : getUserProfile
 */
@Data
@AllArgsConstructor
public class UserProfileDto {
    private String userName;
    private String userImage;
}
