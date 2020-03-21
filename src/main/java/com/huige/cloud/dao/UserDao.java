package com.huige.cloud.dao;

import com.huige.cloud.model.User;
import com.iw86.base.db.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @Author xiezh
 * @Description
 * @Date 2018/4/2 9:39
 */
@Repository
public interface UserDao extends BaseDao {
    User login(User user);

    User selectByUserName(String username);
}