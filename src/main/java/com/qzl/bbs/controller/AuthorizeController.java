package com.qzl.bbs.controller;

import com.qzl.bbs.dto.AccessTokenDTO;
import com.qzl.bbs.dto.GithubUser;
import com.qzl.bbs.mapper.UserMapper;
import com.qzl.bbs.model.User;
import com.qzl.bbs.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    private final GithubProvider githubProvider;
    private final AccessTokenDTO accessTokenDTO;
    private final UserMapper userMapper;

    @Autowired
    public AuthorizeController(GithubProvider githubProvider, AccessTokenDTO accessTokenDTO, UserMapper userMapper) {
        this.githubProvider = githubProvider;
        this.accessTokenDTO = accessTokenDTO;
        this.userMapper = userMapper;
    }


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8911/callback");
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUesr(accessToken);

        System.out.println(githubUser);

        if (githubUser != null) {
            User user = new User();

            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getLogin());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            System.out.println(user);
            userMapper.insert(user);

            response.addCookie(new Cookie("token", token));
//            request.getSession().setAttribute("user", user);
            return "redirect:index";
        } else {
            return "redirect:index";
        }
    }

}
