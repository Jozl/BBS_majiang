package com.qzl.bbs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenDTO {
    private final String client_id = "1f922d65c45f2b65e23e";
    private final String client_secret = "a6193efaa2c0c434b218315477574f0a78b511cc";
    private String code;
    private String redirect_uri;
    private String state;

}
