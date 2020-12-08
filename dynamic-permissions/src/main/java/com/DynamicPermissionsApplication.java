package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author Tiantun
 * @date 2020/12/8 20:36
 */
@SpringBootApplication
@MapperScan(basePackages="com.dao")
public class DynamicPermissionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicPermissionsApplication.class, args);
    }

}
