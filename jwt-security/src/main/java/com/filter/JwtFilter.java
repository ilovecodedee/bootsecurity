package com.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
/**
 * @author Tiantun
 * @date 2020/12/10 21:40
 */
public class JwtFilter extends GenericFilterBean {
    /**
     1.首先从请求头中提取出 authorization 字段，这个字段对应的value就是用户的token。
     2.将提取出来的token字符串转换为一个Claims对象，再从Claims对象中提取出当前用户名和用户角色，
     创建一个UsernamePasswordAuthenticationToken放到当前的Context中，然后执行过滤链使请求继续执行下去。
     * @author tiantun
     * @date: 2020/12/10 10:08
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String jwtToken = req.getHeader("authorization");
        System.out.println(jwtToken);
        Claims claims = Jwts.parser().setSigningKey("sang@123").parseClaimsJws(jwtToken.replace("Bearer",""))
                .getBody();
        //获取当前登录用户名
        String username = claims.getSubject();
        //获取用户角色
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(req,servletResponse);
    }

}