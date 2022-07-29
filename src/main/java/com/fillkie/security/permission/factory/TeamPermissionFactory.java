package com.fillkie.security.permission.factory;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fillkie.security.permission.TeamPermission;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.bson.json.JsonObject;
import org.springframework.boot.json.JsonParser;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Getter
public class TeamPermissionFactory {

    private String FILE_DIR = "permission/permission_code.json";
    private ClassPathResource permissionResource;

    private TeamPermission teamPermission;

    public TeamPermissionFactory() throws IOException {
        teamPermission = addTeamPermission();
    }

    private TeamPermission addTeamPermission() throws IOException {
        log.info("Adding Team Permission");

        readFile(FILE_DIR);

        InputStream inputStream = getStreamFromResource(permissionResource);

        TeamPermission teamPermission = mapTeamPermission(inputStream);

        return teamPermission;
    }

    private void readFile(String fileDir){
        log.info("read permission_code.json");
        permissionResource = new ClassPathResource(fileDir);
    }

    private TeamPermission mapTeamPermission(InputStream inputStream) throws IOException {
        log.info("Start Team Permission Mapping");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        HashMap<String, Integer> teamObject = (HashMap<String, Integer>) objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {}).get("team");
        TeamPermission teamPermission = objectMapper.convertValue(teamObject, TeamPermission.class);

        return teamPermission;
    }

    private InputStream getStreamFromResource(ClassPathResource resource)
        throws IOException {
        InputStream inputStream = resource.getInputStream();

        if (inputStream == null) {
            throw new IllegalArgumentException("menu file \"" + FILE_DIR + "\" not found.");
        } else {
            return inputStream;
        }
    }

}
