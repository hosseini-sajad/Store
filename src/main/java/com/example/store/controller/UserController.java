package com.example.store.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("home/login");
    }

    @GetMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("home/register");
    }
}
