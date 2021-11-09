package com.qzl.bbs.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

    @Value("${github.client.id}")
    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    @Value("${github.client.secret}")
    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }
}
