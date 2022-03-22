package com.parrot.orders.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.parrot.orders.model.dto.AuthenticationRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerIT {

	private AuthenticationRequest user;
	
	private String loginResponse;
	
	@LocalServerPort
	private int port;

	@Before
	public void init() throws JsonProcessingException {
		
		user = new AuthenticationRequest("francisco.perez@parrot.com");
		
		loginResponse ="francisco.perez@parrot.com";

	}

	
	@Test
	public void login() throws Exception{
		
		HttpHeaders headers = new HttpHeaders();
		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpEntity<?> entity = new HttpEntity<>(user, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/login"),
				HttpMethod.POST, entity, String.class);

		HttpStatus httpStatus = response.getStatusCode();

		assertEquals(HttpStatus.OK,httpStatus);
		assertTrue(response.getBody().contains(loginResponse));

	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
