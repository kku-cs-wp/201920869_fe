package kr.ac.kku.cs.wp.wsd.user.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;
import kr.ac.kku.cs.wp.wsd.tools.message.MessageException;
import kr.ac.kku.cs.wp.wsd.user.dto.UserDTO;
import kr.ac.kku.cs.wp.wsd.user.dto.UserRoleDTO;
import kr.ac.kku.cs.wp.wsd.user.entity.User;
import kr.ac.kku.cs.wp.wsd.user.mapper.UserMapper;
import kr.ac.kku.cs.wp.wsd.user.service.UserService;

/**
 * UserController
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@Controller
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	
	
	@RequestMapping("/user/userlist")
	public ModelAndView userList() {
		
		List<User> users = userService.getUsers(null);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", users);
		mav.setViewName("/user/userList");
		
		return mav;
	}
	
	@RequestMapping("/user/search")
	public ModelAndView searchUser(@RequestParam(name = "queryString") String queryString) {
		List<User> users = null;
		
		logger.debug("queryString: {}", queryString);
		
		users = userService.getUsersByQueryString(queryString);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", users);
		mav.setViewName("/user/userCards");
		
		return mav;
	}
	
	@RequestMapping(value="/user/form")
	public ModelAndView userForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/user/userForm");
		mav.addObject("userDTO", new UserDTO());
		
		return mav;
	}
	
	@RequestMapping(value="/user/edit")
	public ModelAndView userEdit(@RequestParam(name = "userId", required = true) String userId) {
		ModelAndView mav = new ModelAndView();
		
		User user = userService.getUserById(userId);
		mav.setViewName("/user/userEdit");
		mav.addObject("user", user);
		
		return mav;
	}
	
	@RequestMapping(value="/user/update")
	public ModelAndView updateUser(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult, @RequestParam(value = "photoFile", required = false) MultipartFile photoFile) {
		ModelAndView mav = new ModelAndView();
		
		logger.debug("bindingResult.hasErrors {} ", bindingResult.hasErrors());
		
		boolean hasErrors = false;
		
		List<ObjectError> errors = new ArrayList<ObjectError>();
		
		if (bindingResult.hasErrors()) {
			
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			
			for (ObjectError objectError : allErrors) {
				if (!objectError.getCode().equals("NotBlank")) {
					hasErrors = true;
					errors.add(objectError);
					logger.debug("objectError args {} code {}", objectError.getArguments(), objectError.getCode());
				}
			}
		} 
		
		logger.debug("hasErrors {}", hasErrors);
		
		if (hasErrors) {
			StringBuffer errorMessage = new StringBuffer();
			
			for (ObjectError objectError : errors) {
				errorMessage.append(objectError.getDefaultMessage()).append("<br>");
			}
			
			throw new MessageException(errorMessage.toString());
			
		} else {
			
			User user = userMapper.toEntity(userDTO);
			
			setPhoto(photoFile, user);
			
			logger.debug("photo : {}", user.getPhoto());
			
			User updatedUser =  userService.updateUser(user);
			
			logger.debug("updateUser name : {}", updatedUser.getName());
			
			mav.setViewName("/user/userView");
	        mav.addObject("user", updatedUser);
	        
		}
		
		return mav;
	}
	
	@RequestMapping(value="/user/view")
	public ModelAndView userView(@RequestParam(name = "userId", required = true) String userId) {
		User user = userService.getUserById(userId);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/user/userView");
		mav.addObject("user", user);
		
		return mav;
	}
	
	@RequestMapping(value="/user/create")
	public ModelAndView createUser(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult, @RequestParam(value = "photoFile", required = false) MultipartFile photoFile) {
		ModelAndView mav = new ModelAndView();
		
		logger.debug("bindingResult.hasErrors {} ", bindingResult.hasErrors());
		
		if (bindingResult.hasErrors()) {
			List<ObjectError> errors = bindingResult.getAllErrors();
			StringBuffer errorMessage = new StringBuffer();
			
			for (ObjectError objectError : errors) {
				errorMessage.append(objectError.getDefaultMessage()).append("<br>");
			}
			
			throw new MessageException(errorMessage.toString());
					
			
		} else {
			
			List<UserRoleDTO> urDTO = userDTO.getUserRoles();
			
			if (urDTO == null) {
				logger.debug("urDTO is null");
			}
			
			User user = userMapper.toEntity(userDTO);
			
			setPhoto(photoFile, user);
			
			logger.debug("photo : {}", user.getPhoto());
			
			User createdUser = userService.createUser(user);
			
			mav.setViewName("/user/userView");
	        mav.addObject("user", createdUser);
		}
        
        return mav;
	}
	
	
	@RequestMapping("/user/delete")
	public ModelAndView deleteUser(@ModelAttribute User user) {
		ModelAndView mav = new ModelAndView();
		
		User existingUser = userService.getUser(user);
		
		userService.deleteUser(existingUser);
		
		mav.addObject("user", existingUser);
		mav.setViewName("/user/userDeleted");
		
		return mav;
	}
	
	private void setPhoto(MultipartFile photoFile, User user) {
        if (photoFile != null && !photoFile.isEmpty()) {
            try {
                String uploadDir = "/tmp/uploads/";
                
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                
                String originalFilename = photoFile.getOriginalFilename();
                
                String filename = UUID.randomUUID().toString() + "_" + originalFilename;
                
                File destinationFile = new File(dir, filename);
                
                photoFile.transferTo(destinationFile);
                
                user.setPhoto(filename);
                
            } catch (IOException e) {
                logger.error("파일 저장 중 오류 발생", e);
                throw new MessageException("fileUploadError", e);
            }
        }
	}
	
}