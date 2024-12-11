package kr.ac.kku.cs.wp.wsd.user.dao;

import java.util.List;

import kr.ac.kku.cs.wp.wsd.user.entity.User;

/**
 * UserDAO
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
public interface UserDAO {
	
	public User getUserById(String userId);
	public User getUser(User user);
	public User updateUser(User user);
	public void deleteUser(User user);
	public User createUser(User user);
	public List<User> getUsers(User user);
}