package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class ConfigsecurityApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void getPasswore() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("111"));
        System.out.println(passwordEncoder.encode("111"));
        System.out.println(passwordEncoder.encode("111"));
        System.out.println(new BCryptPasswordEncoder().matches("111", "$2a$10$gmZ5EOpUeqCHo8vFFWssJexAa1NVrSOynYoLBRG.p2e850mT6D22y"));
        System.out.println(new BCryptPasswordEncoder().matches("222", "$2a$10$gmZ5EOpUeqCHo8vFFWssJexAa1NVrSOynYoLBRG.p2e850mT6D22y"));

    }

}
