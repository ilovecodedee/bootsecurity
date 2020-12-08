package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * EnableGlobalMethodSecurity:
    第一个参数是开启两个注解（方法执行前和在方法执行后，方法执行后用的比较少）
   ，第二个是可以写表达式控制方法安全
 * @author Tiantun
 * @date 2020/12/7
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled=true,securedEnabled = true)
public class ConfigsecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigsecurityApplication.class, args);
    }

}
