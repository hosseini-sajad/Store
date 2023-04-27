package com.example.store.controller;

import com.example.store.core.Constant;
import com.example.store.core.StoreException;
import com.example.store.core.UserRole;
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
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("home/login", new HashMap<>() {{
            put("UserDto", new UserDto());
        }});
    }
    @PostMapping(value = "/login")
    public ModelAndView login(@ModelAttribute UserDto userDto, HttpServletRequest servletRequest) {
        try {
            User user = userService.login(userDto);
            servletRequest.getSession().setAttribute(Constant.USER_SESSION, user);
            if (user.getRole() != UserRole.User){
                return new ModelAndView("redirect:/user/admin");
            } else {
                return new ModelAndView("redirect:/user/login");
            }
        } catch (StoreException e) {
            return new ModelAndView("error", new HashMap<>(){{
                put("code", e.getCode());
                put("message", e.getMessage());
            }});
        }
    }

    @GetMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("home/register", new HashMap<>() {{
            put("user", new User());
        }});
    }
    @PostMapping(value = "/signup")
    public ModelAndView signup(@ModelAttribute User user, HttpServletRequest servletRequest) {
        try {
            userService.signup(user);
            return new ModelAndView("redirect:/user/login");
        } catch (StoreException e) {
            return new ModelAndView("error", new HashMap<>(){{
                put("code", e.getCode());
                put("message", e.getMessage());
            }});
        }
    }

    @GetMapping("/admin")
    public ModelAndView admin(HttpServletRequest servletRequest) {
        if (servletRequest.getSession().getAttribute(Constant.USER_SESSION) == null) {
            return new ModelAndView("home/login", new HashMap<>(){{
                put("user", new UserDto());
            }});
        }
        return new ModelAndView("admin/index");
    }

    @GetMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest servletRequest) {
        servletRequest.getSession().removeAttribute(Constant.USER_SESSION);
        return new ModelAndView("redirect:/");
    }
}
