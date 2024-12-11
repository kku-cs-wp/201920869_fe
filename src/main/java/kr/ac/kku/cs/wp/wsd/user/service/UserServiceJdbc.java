package kr.ac.kku.cs.wp.wsd.user.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.ac.kku.cs.wp.wsd.user.entity.User;
import kr.ac.kku.cs.wp.wsd.user.repository.UserJdbcRepository;

/**
 * UserServiceJdbcImpl
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@Service
public class UserServiceJdbc {

	private static final Logger logger = LogManager.getLogger(UserServiceJdbc.class);
	
	@Autowired
	private UserJdbcRepository ujr;
	
	@Transactional(transactionManager = "jdbcTransactionManager")
	public List<User> getUsers() {
		return ujr.findAll();
	}
	
}