package com.parrot.orders.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.parrot.orders.service.security.JwtTokenProvider;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql("/report_test.sql")
public class ProductControllerIT {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@LocalServerPort
	private int port;

	String report;

	@Before
	public void init() throws JsonProcessingException {

		report = "Tacos pastor";
	}

	@Test
	public void reportProducts() throws Exception {

		String jwt = "Bearer " + jwtTokenProvider.createToken("lorena.calvo@parrot.com");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", jwt);
		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String, Object>>(headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/product?initDate=2022-01-01&endDate=2022-05-05"), HttpMethod.GET, entity,
				String.class);

		HttpStatus httpStatus = response.getStatusCode();

		assertEquals(HttpStatus.OK, httpStatus);

		assertTrue(response.getBody().contains(report));

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
