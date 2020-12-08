package com.example.severice;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * @author tiantun
 * @description:
 * @date 2020/12/7
 */
@Service
public class MethodSecuritySevice {
    @PreAuthorize("hasRole('admin')")
    public String admin(){
        return "hello admin";
    }
    @Secured("ROLE_user")
    public String user(){
        return "hello user";

    }
    @PreAuthorize("hasAnyRole('admin','user')")
    public String hello(){
        return "hello admin and user";
    }
}
