package com.fillkie.security.config.interceptor;

import com.fillkie.security.advice.exception.NoPermissionException;
import com.fillkie.service.TeamPermissionService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

@Component
@RequiredArgsConstructor
@Slf4j
public class InviteUserPermissionInterceptor implements HandlerInterceptor {

    private final TeamPermissionService teamPermissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        // userId
        String userId = (String) request.getAttribute("id");

        // pathVariable teamId
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String teamId = (String) pathVariables.get("teamId");
        log.info(">>> UpdatePermissionInterceptor teamId : {}", teamId);

        if(!teamPermissionService.checkInvitePermission(userId, teamId)){
            throw new NoPermissionException("Permission Invite 권한이 없습니다!");
        }

        return true;

    }
}
