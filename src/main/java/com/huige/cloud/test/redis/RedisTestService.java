package com.huige.cloud.test.redis;

import com.huige.cloud.dao.UserDao;
import com.huige.cloud.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisTestService {
    private static final Log log = LogFactory.getLog(RedisTestService.class);

    @Resource
    private UserDao userDao;

    @Cacheable(value = "getUserByName")
    public User getUserByName(String usrename) {
        log.info("----------getUserByName---------------");
        return userDao.selectByUserName(usrename);
    }

}
