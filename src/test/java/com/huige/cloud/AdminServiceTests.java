package com.huige.cloud;

import com.huige.cloud.model.User;
import com.huige.cloud.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTests {
	@Resource
	private UserService userService;

	@Test
	public void testAddUser() {
		User user = new User();
		user.setUsername("admin");
		user.setTruename("谢智辉");
		user.setPassword("123");
		user.setState(1);
		int userid = userService.addUser(user);
		Assert.assertTrue(userid > 0 || userid == -2);
	}

}
