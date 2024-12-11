package kr.ac.kku.cs.wp.wsd.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import kr.ac.kku.cs.wp.wsd.user.entity.User;

/**
 * UserDAOSHImpleTest
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/app-context.xml")
@WebAppConfiguration
public class UserServiceSHImpleTest {

	private static final Logger logger = LogManager.getLogger(UserServiceSHImpleTest.class);
	
	
	@Autowired
	@Qualifier("userServiceJpaImpl")
	private UserService userService;


	@Test
	public void testGetUser() {
		User user = userService.getUserById("kku_1000");
		
		assertEquals(user.getId(), "kku_1000");
		
		logger.debug("user id : {}" , user.getId());
	}
}