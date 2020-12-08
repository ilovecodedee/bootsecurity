package com.example.dbsecurity;

import com.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class DbsecurityApplicationTests {

    @Autowired
    UserServiceImpl userServiceImpl;
    @Test
    void contextLoads() {
    }
    @Test
    public void gets(){

        UserDetails admin = userServiceImpl.loadUserByUsername("admin");
        System.out.println(admin);
    }

}
