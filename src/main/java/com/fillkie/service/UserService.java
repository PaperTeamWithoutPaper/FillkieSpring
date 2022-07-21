package com.fillkie.service;

import com.fillkie.security.advice.exception.UserNotFoundException;
import com.fillkie.security.config.jwt.JwtTokenProvider;
import com.fillkie.controller.responseDto.TeamListResDto;
import com.fillkie.domain.user.User;
import com.fillkie.domain.UserTeam;
import com.fillkie.oauthService.google.GoogleUser;
import com.fillkie.oauthService.google.OAuthToken;
import com.fillkie.repository.UserRepository;
import com.fillkie.repository.UserTeamRepository;
import com.fillkie.service.dto.AccessRefreshDto;
import com.fillkie.service.dto.UserProfileDto;
import com.fillkie.service.oauth.OAuthService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

  private Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserRepository userRepository;
  private final UserTeamRepository userTeamRepository;
  private final OAuthService oauthService;
  private final JwtTokenProvider jwtTokenProvider;

//    public UserService(UserRepository userRepository, OAuthService oauthService, JwtTokenProvider jwtTokenProvider) {
//        this.userRepository = userRepository;
//        this.oauthService = oauthService;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }

  @Transactional
  public AccessRefreshDto oauthLogin(String code) {
    ResponseEntity<String> accessTokenResponse = oauthService.createPostRequest(code);
    System.out.println("UserService body : " + accessTokenResponse.getBody());
    OAuthToken oAuthToken = oauthService.getAccessToken(accessTokenResponse);
    System.out.println("UserService oAuthToken toString : " + oAuthToken.toString());
    logger.info("infos : {}", oAuthToken.toString());
    logger.info("Access Token: {}", oAuthToken.getAccessToken());

    ResponseEntity<String> userInfoResponse = oauthService.createGetRequest(oAuthToken);
    GoogleUser googleUser = oauthService.getUserInfo(userInfoResponse);
    logger.info("Google User Name: {}", googleUser.getName());
    logger.info("Token Response: {}", userInfoResponse.toString());

    if (!isJoinedUser(googleUser)) {
      signUp(googleUser, oAuthToken);
    }
    User user = userRepository.findByEmail(googleUser.getEmail())
        .orElseThrow(UserNotFoundException::new);
    updateToken(user, oAuthToken);

    log.info("UserService Login User : {}", user.toString());
    return new AccessRefreshDto(jwtTokenProvider.createAccessToken(user.getId(), user.getEmail()), jwtTokenProvider.createRefreshToken(user.getId(), user.getEmail()));
  }

//  public String addUserTeam(String userId, String userTeamId){
//    User user = getUser(userId);
//    user.addUserTeamId(userTeamId);
//    return userRepository.save(user).getId();
//  }

  // 예외 처리 필요
  private User getUser(String userId){
    return userRepository.findById(userId).orElseThrow(RuntimeException::new);
  }
  public List<TeamListResDto> getTeamList(String userId){
    List<UserTeam> userTeams =
        userTeamRepository.findByUserId(userId);
    List<TeamListResDto> teamListResDtos = new ArrayList<>();
    for(int i = 0 ; i < userTeams.size() ; i++){
      teamListResDtos.add(new TeamListResDto(i, userTeams.get(i).getTeamId(), null, userTeams.get(i).getTeamName()));
    }
//    int idx = 0;
//    userTeams.stream().forEach(userTeam -> teamListResDtos.add(new TeamListResDto(idx, userTeam.getTeamId(), null, userTeam.getTeamName())));
    return teamListResDtos;
  }

  public UserProfileDto getUserProfile(String userId){
    User user = getUser(userId);
    return new UserProfileDto(user.getName(), user.getImage());
  }

  // 예외 처리 필요
  private boolean isJoinedUser(GoogleUser googleUser) {
    Optional<User> users = userRepository.findByEmail(googleUser.getEmail()); // email로 DB 접근
    logger.info("Joined User: {}", users);
    return users.isPresent();
  }

  private void signUp(GoogleUser googleUser, OAuthToken oAuthToken) {
    User user = googleUser.toUser(oAuthToken.getExpiryDate(), oAuthToken.getAccessToken(), oAuthToken.getRefreshToken());
    userRepository.insert(user);
  }

  private void updateToken(User user, OAuthToken oAuthToken){
    user.setAccessToken(oAuthToken.getAccessToken());
    user.setRefreshToken(oAuthToken.getRefreshToken());
    userRepository.save(user);
  }

}