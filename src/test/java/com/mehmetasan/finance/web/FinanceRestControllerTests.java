package com.mehmetasan.finance.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class FinanceRestControllerTests {
	private RestTemplate restTemplate;

	@Before
	public void setUp() {
		restTemplate = new RestTemplate();
	}
	
	@Test
	public void testGetAccounts() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:5050/finance/rest/accounts", List.class);
		List<Map<String,String>> body = response.getBody();

		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<String> firstNames = body.stream().map(e->e.get("name")).collect(Collectors.toList());
		
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Family", "Retired", "MyAccount", "Other"));
	}

}
