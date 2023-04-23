package com.example.store.controller;

import com.example.store.core.Contents;
import com.example.store.core.StoreException;
import com.example.store.dto.UserDto;
import com.example.store.model.User;
import com.example.store.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping(value = "admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public ModelAndView admin(HttpServletRequest servletRequest) {
        if (servletRequest.getSession().getAttribute(Contents.USER_SESSION) == null) {
            return new ModelAndView("admin/login", new HashMap<>(){{
                put("user", new UserDto());
            }});
        }
        return new ModelAndView("admin/index");
    }

    @PostMapping(value = "/login")
    public ModelAndView login(@ModelAttribute UserDto userDto, HttpServletRequest servletRequest) {
        try {
            User user = userService.login(userDto);
            servletRequest.getSession().setAttribute(Contents.USER_SESSION, user);
            return new ModelAndView("redirct:/admin");
        } catch (StoreException e) {
            return new ModelAndView("error", new HashMap<>(){{
                put("code", e.getCode());
                put("message", e.getMessage());
            }});
        }
    }
    @GetMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest servletRequest) {
        servletRequest.getSession().removeAttribute(Contents.USER_SESSION);
        return new ModelAndView("redirect:/");
    }

}
