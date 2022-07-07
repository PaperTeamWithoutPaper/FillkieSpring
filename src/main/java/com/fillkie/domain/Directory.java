package com.fillkie.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "directory")
@Getter
public class Directory {

    @Id
    private String id;
    private String path;
    private String parentDir;
    private ArrayList<String> childDir = new ArrayList<>();
    private ArrayList<String> childFile = new ArrayList<>();

}
