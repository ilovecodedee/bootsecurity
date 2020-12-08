package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @description: @Component
 * 这是多SecurityConfig的配置
 * @author tiantun
 * @date 2020/12/7
 */
public class MultipleSecurityConfig {

    /**
     @Bean
     密码一般使用单向加密方式，
         传统的：md5不是很安全，因为两个一样的明文加密成的明文一样，可以密码加盐导致不一样，但需要维护盐
         Springboot:提供了一种方式PassEncoder天然明文相同但密码不一样，还可以额外加强度,就不用维护盐了：
     * @author tiantun
     * @date: 2020/12/7
     */
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     不设置用户时，默认用户时user，密码是控制台打印的密码
      * @author tiantun
     * @date: 2020/12/7
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("wrs").password("111").roles("user")
                .and()
                .withUser("管理员").password("222").roles("admin");
    }


    /**
     * order数字越小，这个securityconfig越小执行
       @Configuration
       @Order(1)
     * @author tiantun
     * @date: 2020/12/7
     */
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //对admin方法加权限，多其它方法不加放流
            http.antMatcher("/admin/**").authorizeRequests().anyRequest().hasAnyRole("admin");

        }

    }


    /**
     @Configuration
      * @author tiantun
     * @date: 2020/12/7
     */
    public static class OtherSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginProcessingUrl("/Loginnnn")
                    .permitAll()
                    .and()
                    .csrf().disable();

        }


        }

}

