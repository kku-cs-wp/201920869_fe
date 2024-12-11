package kr.ac.kku.cs.wp.wsd.user.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import kr.ac.kku.cs.wp.wsd.tools.message.MessageException;
import kr.ac.kku.cs.wp.wsd.user.dao.UserDAO;
import kr.ac.kku.cs.wp.wsd.user.entity.User;

/**
 * UserServiceImple
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
//@Service
public class UserServiceHImpl implements UserService {
	
	private static final Logger logger = LogManager.getLogger(UserServiceHImpl.class);
	
	@Autowired
	private UserDAO dao;
	

	@Override
	public User getUserById(String userId) {
		// TODO Auto-generated method stub
		User user= dao.getUserById(userId);
		
		if (user == null) {
			throw new MessageException("Not Found User : " + userId);
		}
		
		return user;
	}

	@Override
	public User getUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		return dao.createUser(user);
		
	}

	@Override
	public List<User> getUsers(User user) {
		// TODO Auto-generated method stub
		return dao.getUsers(user);
	}
	
	@Override
	@Transactional
	public List<User> getUsersByQueryString(String queryString) {
		return null;
	}
	
}