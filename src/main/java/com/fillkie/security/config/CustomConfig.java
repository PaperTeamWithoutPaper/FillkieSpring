package com.fillkie.security.config;

import com.fillkie.security.permission.factory.TeamPermissionFactory;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CustomConfig {
    @Bean
    public TeamPermissionFactory teamPermissionInitializer() throws IOException {
        log.info("Initialize TeamPermission");
        return new TeamPermissionFactory();
    }
}
