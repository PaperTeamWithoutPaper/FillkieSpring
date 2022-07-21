package com.fillkie.domain.user;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
public class Google {
    @Field("expiryDate")
    private Long expiryDate;
    @Field("rootDir")
    private String rootDir;
    @Field("accessToken")
    private String accessToken;
    @Field("refreshToken")
    private String refreshToken;

    @Builder
    public Google(Long expiryDate, String accessToken, String refreshToken) {
        this.expiryDate = expiryDate;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }



}
