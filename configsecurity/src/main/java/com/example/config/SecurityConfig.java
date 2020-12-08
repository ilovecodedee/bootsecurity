package com.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tiantun
 * @description:
 * @date 2020/12/6
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 方法做的事情和实现的功能是：
     * @author tiantun
     * @date: 2020/12/7
     */
        @Bean
        PasswordEncoder passwordEncoder() {
            //return new BCryptPasswordEncoder();
            return NoOpPasswordEncoder.getInstance();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("wrs").password("111").roles("user")
                    .and()
                    .withUser("管理员").password("222").roles("admin");
        }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**").access("hasAnyRole('user','admin')")
                //.antMatchers("/admin/**").hasAnyRole("user","admin")
                //除此之外还可以设置同时拥有user和admin属性
                .antMatchers("/admin/**").hasRole("admin")
                //其它请求只需要登录认证就可以访问
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
               .logoutSuccessHandler(new LogoutSuccessHandler(){
                   @Override
                   public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                       resp.setContentType("application/json;charset=utf-8");
                       PrintWriter out = resp.getWriter();
                       Map<String, Object> map = new HashMap<>(18);
                       map.put("status", 200);
                       map.put("msg", authentication.getPrincipal());
                       out.write(new ObjectMapper().writeValueAsString(map));
                       out.flush();
                       out.close();
                   }
               })
                .and()


                .formLogin()
                //不需要登录就能访问该接口，可以作为登录接口  也是springsecurity登录页面的链接
                .loginProcessingUrl("/doLogin")
                .passwordParameter("pas")
                .usernameParameter("name")
                // 登录成功后页面跳转，也可以登录成功后走下面的json
                //.loginPage("/pleaseLogin")
                //登录doLogin成功的json返回  前后端分离根据json前端自动跳转页面
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        Map<String, Object> map = new HashMap<>(18);
                        map.put("status", 200);
                        map.put("msg", authentication.getPrincipal());
                        out.write(new ObjectMapper().writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                //登录doLogin成功的json返回
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        Map<String, Object> map = new HashMap<>(18);
                        map.put("status", 401);
                        if (e instanceof LockedException) {
                            map.put("msg", "账户被锁定了，登录失败");
                        } else if (e instanceof BadCredentialsException) {
                            map.put("msg", "用户名或密码输入错误，登录失败");
                        } else if (e instanceof DisabledException) {
                            map.put("msg", "账户被禁用，登录失败");
                        } else if (e instanceof AccountExpiredException) {
                            map.put("msg", "账户过期，登录失败");
                        } else if (e instanceof CredentialsExpiredException) {
                            map.put("msg", "密码过期，登录失败");
                        } else {
                            map.put("msg", "登录失败");

                        }
                        out.write(new ObjectMapper().writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf().disable();
    }

}
