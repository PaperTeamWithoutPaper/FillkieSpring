package com.fillkie.security.config.interceptor;

import com.fillkie.security.advice.exception.NoPermissionException;
import com.fillkie.service.TeamPermissionService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 팀의 Group, User에 대한 권한 update 권한 인가
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UpdatePermissionInterceptor implements HandlerInterceptor {

    private final TeamPermissionService teamPermissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        String userId = (String) request.getAttribute("id");
        String teamId = request.getParameter("teamId");

        if(!teamPermissionService.CheckUpdatePermission(userId, teamId)){
            throw new NoPermissionException("Permission Update 권한이 없습니다!");
        }


        return true;
    }
}
