package com.qzl.bbs.controller;

import com.qzl.bbs.mapper.UserMapper;
import com.qzl.bbs.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @GetMapping({"/", "index"})
    public String index(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token") && cookie.getValue() != null) {
                    User user = userMapper.findByToken(cookie.getValue());
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
//                        return "redirect:index";
                    }
                    break;
                }
            }
        }
        return "index";
    }

}
