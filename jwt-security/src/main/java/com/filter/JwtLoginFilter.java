package com.filter;

import com.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
/**
 * 1.一个是用户登录的过滤器，在用户的登录的过滤器中校验用户是否登录成功，
    如果登录成功，则生成一个token返回给客户端，登录失败则给前端一个登录失败的提示。
 * 2.经调试发现无论是前面的json-login模块还是这里的jwt-security模块都是调用了AbstractAuthenticationProcessingFilter的doflidter
     只不过根据多态性调用了子类的attemptAuthentication方法已达到自写登录认证逻辑的目的
 *
 *
 * @author Tiantun
 * @date 2020/12/10 9:45
 */

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    public  JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        //登录认证管理器，这里向设置，之后登录会使用
        setAuthenticationManager(authenticationManager);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationException, IOException, ServletException {
        //将前端的json请求转化为user对象，这里也可以像json-login模块一样转化为map
        User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
        //调用认证方法检查用户名密码是否正确，这里使用的就是security的登录方法，和json-login一样
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        StringBuffer as = new StringBuffer();
        //取出登录用户的角色
        for (GrantedAuthority authority : authorities) {
            as.append(authority.getAuthority())
                    .append(",");
        }
        String jwt = Jwts.builder()
                //配置角色、主题、过期时间以及加密算法和密钥
                .claim("authorities", as)
                .setSubject(authResult.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512,"sang@123")
                .compact();
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String json = new ObjectMapper().writeValueAsString(jwt);
        out.write(new ObjectMapper().writeValueAsString(json));
        out.flush();
        out.close();
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse resp, AuthenticationException failed) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write("登录失败!");
        out.flush();
        out.close();
    }
}