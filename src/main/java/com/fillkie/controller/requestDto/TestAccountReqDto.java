package com.fillkie.controller.requestDto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * UserController : testAccount
 */
@Data
@AllArgsConstructor
public class TestAccountReqDto {
    @NotBlank
    private String email;
}
