package com.qzl.bbs.controller;

import com.qzl.bbs.dto.AccessTokenDTO;
import com.qzl.bbs.dto.GithubUser;
import com.qzl.bbs.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    AccessTokenDTO accessTokenDTO = new AccessTokenDTO();


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {

        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8911/callback");
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser uesr = githubProvider.getUesr(accessToken);
        System.out.println(uesr);

        return "index";
    }

}
