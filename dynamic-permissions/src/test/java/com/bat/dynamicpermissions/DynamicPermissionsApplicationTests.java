package com.bat.dynamicpermissions;

import com.dao.MenuDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DynamicPermissionsApplicationTests {

    @Autowired
    MenuDao menuDao;
    @Test
    void contextLoads() {
    }

    @Test
    public void testGetAllMenus(){
        System.out.println(menuDao.getAllMenus());
    }

}
