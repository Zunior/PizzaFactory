package com.example.PizzaHut;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.PizzaHut.modules.pizza.dto.PizzaDto;
import com.example.PizzaHut.modules.pizza.service.PizzaServiceImpl;


//import org.junit.Assert;
//import org.skyscreamer.jsonassert.JSONAssert;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PizzaControllerTests {
	
	Logger logger = LoggerFactory.getLogger(PizzaControllerTests.class);
	
	@Autowired
	PizzaServiceImpl pizzaService;
	
	@MockBean
	Clock clock;
	
	@Autowired
	PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    ApplicationContext context;
	
	@LocalServerPort
    private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	
	@Test
	public void testRetrievePizza() {
		HttpEntity<PizzaDto> entity = new HttpEntity<PizzaDto>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
		          createURLWithPort("/pizzaFactory/capricciosa"), HttpMethod.GET, entity, String.class);
		
		String expected = "{\"slug\":\"capricciosa\",\"name\":\"Capricciosa\",\"size\":240,\"price\":20}";
//		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertEquals(expected, response.getBody());
		
		//Verify bad request and missing header
//	    Assert.assertEquals(400, response.getStatusCodeValue());
//	    Assert.assertEquals(true, response.getBody().contains("Missing request header"));
	}
	

	
	private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
