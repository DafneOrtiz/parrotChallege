package com.parrot.orders.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.parrot.orders.model.dto.AuthenticationRequest;
import com.parrot.orders.repository.UserRepository;
import com.parrot.orders.service.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController {
  
   
    @Autowired
    JwtTokenProvider jwtTokenProvider;
	
    @Autowired
    UserRepository userRepository;
    
    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> signin(@RequestBody AuthenticationRequest data) {

        try {
            String email = data.getEmail();
            if(!userRepository.existsUserByEmail(email)) {
            	throw new ResponseStatusException(
    	                HttpStatus.FORBIDDEN, "Access denied");	

            }
            String token = jwtTokenProvider.createToken(email);
            Map<Object,Object> model = new HashMap<>();
            model.put("username", email);
            model.put("token", token);
            return new ResponseEntity<Map<Object, Object>>(model, HttpStatus.OK);
            } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
    
    @PostMapping("/api/logout")
    public ResponseEntity<String> logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		return new ResponseEntity<>("Login successfully", HttpStatus.OK);
    }
}
