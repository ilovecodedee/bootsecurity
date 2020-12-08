package com.filter;

import com.entity.Menu;
import com.entity.Role;
import com.service.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author tiantun
 * @description:
 * @date 2020/12/8 14:50
 */
@Component
public class MyFilter implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    MenuServiceImpl menuService;

    /**
     * 方法做的事情和实现的功能是:获取当前请求资源所需要的角色
     * @param: null
     * @return:
     * @author tiantun
     * @date: 2020/12/8 20:06
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = null;
        if (o instanceof FilterInvocation) {
            requestUrl = ((FilterInvocation) o).getRequestUrl();
        }
        List<Menu> menus = menuService.getAllMenus();
        for (Menu menu : menus) {
            if (pathMatcher.match(menu.getPattern(), requestUrl)) {
                List<Role> roles = menu.getRoles();
                String[] roleArr = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    roleArr[i] = "ROLE_" + roles.get(i).getName();
                }
                return SecurityConfig.createList(roleArr);
            }
        }
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
