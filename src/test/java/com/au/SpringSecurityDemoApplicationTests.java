package com.au;

import com.au.dao.UserMapper;
import com.au.model.DO.UserDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurityDemoApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void test1() {
		UserDO user = userMapper.getUserByUsername("au");
		System.out.println(user);
	}

}
