package kr.ac.kku.cs.wp.wsd.user.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.ac.kku.cs.wp.wsd.user.entity.User;
import kr.ac.kku.cs.wp.wsd.user.service.UserService;

/**
 * UserRestController
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private static final Logger logger = LogManager.getLogger(UserRestController.class);
	
	@Autowired
	@Qualifier("userServiceImpl")
    private UserService userService;

	@GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers(null);
        
        return ResponseEntity.ok(users);
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody @Valid User user) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id); // Ensure the ID is set for the update
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        User existingUser = userService.getUserById(id);
        
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        
        userService.deleteUser(existingUser);
        return ResponseEntity.noContent().build();
    }
}