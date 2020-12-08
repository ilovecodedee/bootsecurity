package com.dao;

import com.entity.Menu;

import java.util.List;

/**
 * @author tiantun
 * @description:
 * @date 2020/12/8 14:54
 */
public interface MenuDao {
    /**
     * 方法做的事情和实现的功能是:
       查询所有资源
     * @author tiantun
     * @date: 2020/12/8 14:56
     */
     List<Menu> getAllMenus();
}
