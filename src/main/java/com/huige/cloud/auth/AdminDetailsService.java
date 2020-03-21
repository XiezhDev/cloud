package com.huige.cloud.auth;

import com.huige.cloud.dao.UserDao;
import com.huige.cloud.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *@Author xiezh
 *@Description
 *@Date 2018/4/2 9:24
 */
@Component
public class AdminDetailsService implements UserDetailsService {
    @Resource
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.selectByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        //用于权限判定
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("admin"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authorities);
    }
}
