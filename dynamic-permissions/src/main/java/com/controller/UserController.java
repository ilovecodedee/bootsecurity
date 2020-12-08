package com.controller;


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

    @RequestMapping("/hello")
    public String sayHello() {
        return "hello security";
    }

    @GetMapping("/dba/hello")
    public String dba() {
        return "hello dba";
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


}
