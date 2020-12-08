package com.example.controller;

import com.example.severice.MethodSecuritySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tiantun
 * @description:
 * @date 2020/12/6
 */
@RestController
public class UserController {

    @Autowired
    MethodSecuritySevice methodSecurity;

    @RequestMapping("/hello")
    public String sayHello() {
        return "hello security";
    }

    @GetMapping("/admin/hello")
    public String admin() {
        return "hello admin";
    }

    @GetMapping("/user/hello")
    public String user() {
        return "hello user";
    }

    @GetMapping("/pleaseLogin")
    public String doLogin() {
        return "please login";
    }


    @RequestMapping("/hello1")
    public String sayHello1() {
        return methodSecurity.admin();
    }

    @RequestMapping("/hello2")
    public String sayHello2() {
        return methodSecurity.user();
    }

    @RequestMapping("/hello3")
    public String sayHello3() {
        return methodSecurity.hello();
    }

}
