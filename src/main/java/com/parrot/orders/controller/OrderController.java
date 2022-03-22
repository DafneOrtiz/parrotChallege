package com.parrot.orders.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parrot.orders.model.db.User;
import com.parrot.orders.model.dto.OrderRequest;
import com.parrot.orders.model.dto.OrderResponse;
import com.parrot.orders.service.OrderService;
import com.parrot.orders.service.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/parrot/order")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@PostMapping()
	public ResponseEntity<?> save(@RequestBody OrderRequest order, HttpServletRequest req) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
			order.setUser(new User());
			order.getUser().setEmail(jwtTokenProvider.getUsername(token));		
			Optional<OrderResponse> orderSaved = orderService.processOrder(order);
			
			if (orderSaved.isPresent() && StringUtils.isEmpty(orderSaved.get().getRequestValidationError())) {
				response.put("order", orderSaved.get());
				response.put("mensaje", "The order was created successfully");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			}
			response.put("mensaje", "An error occurred while saving the order");
			response.put("request_validation_error", orderSaved.get().getRequestValidationError());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (Exception e) {
			log.error("Error trying to save order() : " + e);
			e.printStackTrace();
			response.put("mensaje", "An error occurred while saving the order");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
