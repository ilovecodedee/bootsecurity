package com.service;

import com.dao.MenuDao;
import com.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tiantun
 * @description:
 * @date 2020/12/8 15:49
 */
@Service
public class MenuServiceImpl {
    @Autowired
    MenuDao menuDao;
    public List<Menu> getAllMenus(){
        return menuDao.getAllMenus();
    };
}
