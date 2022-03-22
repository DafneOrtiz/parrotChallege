package com.parrot.orders.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.parrot.orders.model.db.User;
import com.parrot.orders.model.dto.AuthenticationRequest;
import com.parrot.orders.model.dto.OrderRequest;
import com.parrot.orders.model.dto.OrderResponse;
import com.parrot.orders.model.dto.ProductOrderDto;
import com.parrot.orders.service.security.JwtTokenProvider;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/order_test.sql")
public class OrdersControllerIT {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	private OrderRequest orderRequest;
	
	private OrderResponse orderResponse;
	
	private String orderResponseJson;

	private ProductOrderDto productOrderOne;

	private ProductOrderDto productOrderTwo;

	private List<ProductOrderDto> productsOrder;
	
	private AuthenticationRequest login;
	
	@LocalServerPort
	private int port;

	@Before
	public void init() throws JsonProcessingException {
		productsOrder = new ArrayList<>();
		productOrderOne = ProductOrderDto.builder().name("Coca cola").unitaryPrice(10.5).amount(2).build();

		productOrderTwo = ProductOrderDto.builder().name("Taco Pastor").unitaryPrice(17.0).amount(10).build();

		productsOrder.add(productOrderOne);
		productsOrder.add(productOrderTwo);

		orderRequest = OrderRequest.builder().clientName("Fransisco Ramirez").TotalPrice(191.0).products(productsOrder)
				.build();
		
		login = new AuthenticationRequest ("francisco.perez@parrot.com");
		
		orderResponse = OrderResponse.builder().clientName("Fransisco Ramirez").TotalPrice(191.0).products(productsOrder).requestValidationError("")
				.build();
		ObjectMapper mapper = new ObjectMapper();  
		orderResponseJson = mapper.writeValueAsString(orderResponse);
	}

	
	@Test
	public void createOrder() throws Exception{
		
		String jwt ="Bearer "+jwtTokenProvider.createToken("francisco.perez@parrot.com");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", jwt);
		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpEntity<OrderRequest> entity = new HttpEntity<OrderRequest>(orderRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/order"),
				HttpMethod.POST, entity, String.class);

		HttpStatus httpStatus = response.getStatusCode();

		assertEquals(HttpStatus.CREATED,httpStatus);
		assertTrue(response.getBody().contains(orderResponseJson));

	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
