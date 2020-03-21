package com.huige.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * 用于生成domain,dao以及mapper文件
 * @author xiezh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Generator {
    @Test
    public void testGenerate() throws Exception {
        DataSource ds = (DataSource) SpringContext.getApplicationContext().getBean("dataSource");
        com.iw86.base.AutoGenerator auto = new com.iw86.base.AutoGenerator(ds);
        auto.create("tuser", "user", "com.huige.cloud", "resources/mapper/");

    }
}
