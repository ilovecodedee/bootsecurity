package com.filter;

import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author tiantun
 * @description:
 * @date 2020/12/8 16:20
 */
@Component
public class MyAccessDecisionManagerImpl implements AccessDecisionManager {
    /**
     * @description:
     *   1.方法做的事情和实现的功能是:
         比较当前登录用户所具有的角色，和当前请求资源所需要角色的关系，
          从而对实现对该资源的管理，可以在这里根据逻辑判断是满足所需一个角色都可，还是都满足才行
         2.第一个参数是登录成功后的认证对象，
          第二个参数用来获取当前请求对象
          第三个是需要哪些角色
         3.经尝试当前用户所拥有的角色，只在登录时获取一次，不同于菜单所需角色每次请求都获取
           可能导致从数据库删除角色，当前用户当次依然能够使用角色，直至当前用户退出
     * @author tiantun
     * @date: 2020/12/8 16:23
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute attribute:collection) {
            if("ROLE_login".equals(attribute.getAttribute())){
                //当前是匿名用户，还没有登录
                if(authentication instanceof  AnonymousAuthenticationToken){
                    throw new AccessDeniedException("非法请求");
                }else{
                    return;
                }
            }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority:authorities) {
                if (authority.getAuthority().equals(attribute.getAttribute())){
                    return;
                }
            }

        }
        throw new AccessDeniedException("非法请求");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
