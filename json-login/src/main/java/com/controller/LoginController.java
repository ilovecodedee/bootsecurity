package com.controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController

public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "尚未登录，请登录";
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}