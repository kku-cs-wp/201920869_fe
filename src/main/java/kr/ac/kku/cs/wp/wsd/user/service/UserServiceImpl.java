package kr.ac.kku.cs.wp.wsd.user.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.ac.kku.cs.wp.wsd.user.dao.UserDAO;
import kr.ac.kku.cs.wp.wsd.user.entity.User;
import kr.ac.kku.cs.wp.wsd.user.repository.UserRepository;
import kr.ac.kku.cs.wp.wsd.user.repository.UserSpecifications;

/**
 * UserServiceSH
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	@Qualifier("userDAOImpl")
	private UserDAO dao;
	
	@Autowired
	private UserRepository userRepository;

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Override
	@Transactional(transactionManager = "hTransactionManager")
	public User getUserById(String userId) {
		User user = dao.getUserById(userId);
		
		if (user == null) {
			throw new MessageException("user not found : " +   userId);
		}
		
		return user;
	}

	@Override
	@Transactional(transactionManager = "hTransactionManager")
	public User getUser(User user) {
		return dao.getUser(user);
	}

	@Override
	@Transactional(transactionManager = "hTransactionManager")
	public User updateUser(User user) {
		return dao.updateUser(user);
	}

	@Override
	@Transactional(transactionManager = "hTransactionManager")
	public void deleteUser(User user) {
		dao.deleteUser(user);
		
	}

	@Override
	@Transactional(transactionManager = "hTransactionManager")
	public User createUser(User user) {
		return dao.createUser(user);
		
	}

	@Override
	@Transactional(transactionManager = "hTransactionManager")
	public List<User> getUsers(User user) {
		return dao.getUsers(user);
	}
	
	@Override
	@Transactional
	public List<User> getUsersByQueryString(String queryString) {
		Specification<User> spec = UserSpecifications.filterUsersByQueryString(queryString);
        return userRepository.findAll(spec);
	}
	
}