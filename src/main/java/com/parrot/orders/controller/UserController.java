package com.parrot.orders.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.parrot.orders.model.db.User;
import com.parrot.orders.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@PostMapping()
	public ResponseEntity<?> save(@RequestBody User user) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			User newUser = userRepository.save(user);
			response.put("user", newUser);
			response.put("mensaje", "The user was created successfully");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

		} catch (Exception e) {
			log.error("Error trying to save user() : " + e);
			response.put("mensaje", "An error occurred while saving the user");
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
