package kr.ac.kku.cs.wp.wsd.user.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import kr.ac.kku.cs.wp.wsd.user.entity.Role;
import kr.ac.kku.cs.wp.wsd.user.entity.User;
import kr.ac.kku.cs.wp.wsd.user.entity.UserRole;
import kr.ac.kku.cs.wp.wsd.user.entity.UserRoleId;

/**
 * UserJDBCRepository
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@Repository
public class UserJdbcRepository {

	private static final Logger logger = LogManager.getLogger(UserJdbcRepository.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<User> findAll() {
		
		RowMapper<User> userRowMappler = new RowMapper<User>() {
			
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPhoto(rs.getString("photo"));
				user.setStatus(rs.getString("status"));
				user.setCreatedAt(rs.getDate("created_at"));
				user.setUpdatedAt(rs.getDate("updated_at"));
				
				
				return user;
			}
		};
			
		String query4User = "SELECT * FROM user ";
		List<User> users =  jdbcTemplate.query(query4User, getRowMapper4User());
		
		RowMapper<UserRole> userRoleRowMappler = new RowMapper<UserRole>() {
			
			@Override
			public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserRole userRole = new UserRole();
				UserRoleId userRoleId = new UserRoleId();
				userRole.setId(userRoleId);
				
				userRole.getId().setUserId(rs.getString("user_id"));
				userRole.getId().setRoleId(rs.getString("role_id"));
				userRole.setRoleName(rs.getString("role"));
				
				Role role = new Role();
				role.setId(rs.getString("role_id"));
				role.setRole(rs.getString("role"));
				
				userRole.setRole(role);
				
				return userRole;
			}
		};
		
		String query4UserRole = "select * from user_role where user_id = ?";
		
		for (User user : users) {
			List<UserRole> userRoles = jdbcTemplate.query((conn) ->{
				PreparedStatement ps = conn.prepareStatement(query4UserRole);
		        ps.setString(1, user.getId());
				
				return ps;
				}, getRowMapper4UserRole());
			
			for (UserRole userRole : userRoles) {
				userRole.setUser(user);
			}
			
			user.setUserRoles(userRoles);
		}
		
		
		return users;
	}
	
	private  RowMapper<User> getRowMapper4User() {
		return (rs, rowNum) -> {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPhoto(rs.getString("photo"));
			user.setStatus(rs.getString("status"));
			user.setCreatedAt(rs.getDate("created_at"));
			user.setUpdatedAt(rs.getDate("updated_at"));
			
			
			return user;
		};
	}
	
	private RowMapper<UserRole> getRowMapper4UserRole() {
		return (rs, rowNum) -> {
			UserRole userRole = new UserRole();
			UserRoleId userRoleId = new UserRoleId();
			userRole.setId(userRoleId);
			
			userRole.getId().setUserId(rs.getString("user_id"));
			userRole.getId().setRoleId(rs.getString("role_id"));
			userRole.setRoleName(rs.getString("role"));
			
			Role role = new Role();
			role.setId(rs.getString("role_id"));
			role.setRole(rs.getString("role"));
			
			userRole.setRole(role);
			
			return userRole;
		};
	}
	
}