package com.qzl.bbs.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String login;
    private long id;
    private String name;
    private String bio;
}
