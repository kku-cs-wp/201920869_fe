package kr.ac.kku.cs.wp.wsd.user.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import kr.ac.kku.cs.wp.wsd.user.dto.UserDTO;
import kr.ac.kku.cs.wp.wsd.user.entity.User;
import kr.ac.kku.cs.wp.wsd.user.mapper.UserMapper;
import kr.ac.kku.cs.wp.wsd.user.service.UserService;

/**
 * UserControllerJpa
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@Controller
public class UserControllerJpa {
	
	private static final Logger logger = LogManager.getLogger(UserControllerJpa.class);
	
	@Autowired
	@Qualifier("userServiceJpaImpl")
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;

	
	@RequestMapping(value="/jpa/user/view")
	public ModelAndView userView(@RequestParam(name = "userId", required = true) String userId) {
		User user = userService.getUserById(userId);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/userView");
		mav.addObject("user", user);
		
		return mav;
	}
	
	@RequestMapping("/jpa/user/userlist")
	public ModelAndView userlist(@RequestParam(name = "queryString") String queryString) {
		ModelAndView mav = new ModelAndView();
		
		logger.debug("queryString : {}", queryString);
		
		List<User> users = userService.getUsersByQueryString(queryString);
		
		mav.addObject("users", users);
		mav.setViewName("/user/userList");
		
		return mav;
	}
	
	@RequestMapping(value="/jpa/user/create")
	public ModelAndView createUser(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		
		
		logger.debug("bindingResult.hasErrors {} ", bindingResult.hasErrors());
		
		if (bindingResult.hasErrors()) {
			mav.setViewName("/user/userForm");
			mav.addObject("errors", bindingResult.getAllErrors());
			
		} else {
			User user = userMapper.toEntity(userDTO);
			
			logger.debug("user id {}", user.getUserRoles().get(0).getId().getUserId());
			logger.debug("role id {}",user.getUserRoles().get(0).getId().getRoleId());
			logger.debug("role name {} ",user.getUserRoles().get(0).getRoleName() );
			logger.debug("role {} ",user.getUserRoles().get(0).getRole() );
			
			userService.createUser(user);
			
			mav.setViewName("/user/userSuccess");
	        mav.addObject("user", userDTO);
		}
        
        return mav;
	}
}