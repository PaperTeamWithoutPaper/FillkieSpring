package com.fillkie.oauthService.google;

import com.fillkie.domain.Team;
import com.fillkie.domain.User;
import lombok.Data;

import java.util.Arrays;
import java.util.HashSet;

@Data
public class GoogleUser {
    public String id;
    public String email;
    public Boolean verifiedEmail;
    public String name;
    public String givenName;
    public String familyName;
    public String picture;
    public String locale;

    public String hd;

    public GoogleUser() {
    }

    public GoogleUser(String id, String email, Boolean verifiedEmail, String name, String givenName, String familyName, String picture, String locale, String hd) {
        this.id = id;
        this.email = email;
        this.verifiedEmail = verifiedEmail;
        this.name = name;
        this.givenName = givenName;
        this.familyName = familyName;
        this.picture = picture;
        this.locale = locale;
        this.hd = hd;
    }

    public User toUser(String accessToken) {
//        Team team1 = new Team();
//        Team team2 = new Team();
//        team1.setTeam("첫번째");
//        team2.setTeam("두번째");
//
//        Team team[] = new Team[2];
//        team[0] = team1;
//        team[1] = team2;
        return User.builder()
                .email(email)
                .name(name)
                .accessToken(accessToken)
                .teams(new HashSet<>())
                .build();
    }

}
