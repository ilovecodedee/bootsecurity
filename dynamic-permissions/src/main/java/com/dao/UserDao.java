package com.dao;

import com.entity.Role;
import com.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tiantun
 * @description:
 * @date 2020/12/7 20:40
 */
public interface UserDao {
    /**
     *通过用户名查询数据库是否有该用户
     * @author tiantun
     * @date: 2020/12/8 12:02
     */
    User loadUserByUsername(String username);

    /**
     * 根据用户的id查询该用户所具有的角色
     * @author tiantun
     * @date: 2020/12/8 12:02
     */
    List<Role> getUserRoleById(Integer uid);
}
