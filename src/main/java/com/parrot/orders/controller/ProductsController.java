package com.parrot.orders.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parrot.orders.model.db.User;
import com.parrot.orders.repository.OrderDetailRepository;
import com.parrot.orders.repository.UserRepository;
import com.parrot.orders.service.security.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/parrot/product")
public class ProductsController {
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@Autowired
	UserRepository   userRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@GetMapping()
	public ResponseEntity<?> findSoldProductsByDate(@RequestParam(required = true)  @DateTimeFormat(pattern="yyyy-MM-dd") Date initDate, @RequestParam (required = true) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate , HttpServletRequest req) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
			User user= userRepository.findUserByEmail(jwtTokenProvider.getUsername(token));
			response.put("report", orderDetailRepository.findSoldProductsByDate(user.getIdUser(), initDate,endDate));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Error trying  create report  find sold products by date {} to {}  " ,initDate, endDate , e);
			response.put("mensaje", "An error occurred while create report find sold products by Date");
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
