package kr.ac.kku.cs.wp.wsd.user.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import kr.ac.kku.cs.wp.wsd.user.entity.User;
import kr.ac.kku.cs.wp.wsd.user.entity.UserRole;

/**
 * UserServiceJdbcTest
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/app-context.xml") 
@WebAppConfiguration
public class UserServiceJdbcTest {

	private static final Logger logger = LogManager.getLogger(UserServiceJdbcTest.class);
	
	@Autowired
	private UserServiceJdbc userServiceJdbc;
	
	@Test
	public void testFindAll() {
		List<User> users = userServiceJdbc.getUsers();
		
		assertTrue(users.size() > 0);
		
		for (User user : users) {
			logger.debug(" user : {}, {}, {}, {}", user.getId(),user.getName(), user.getEmail(), user.getStatus());
			
			List<UserRole> userRoles = user.getUserRoles();
			for (UserRole userRole : userRoles) {
				logger.debug(" {} 's Role : {} :", user.getId(), userRole.getRoleName());
			}
		}
	}
}