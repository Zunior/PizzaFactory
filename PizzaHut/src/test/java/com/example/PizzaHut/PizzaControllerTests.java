package com.example.PizzaHut;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.PizzaHut.modules.pizza.dto.PizzaDto;

import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PizzaControllerTests {
	
	@LocalServerPort
    private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	
	public void testRetrievePizza() {
		HttpEntity<PizzaDto> entity = new HttpEntity<PizzaDto>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
		          createURLWithPort("/pizzaFactory/capricciosa"), HttpMethod.GET, entity, String.class);
	}
	
	String expected = "{\"slug\":\"capricciosa\",\"name\":\"Capricciosa\",\"size\":240,\"price\":20}";
//	assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	//	JSONAssert.assertEquals(expected, response.getBody(), false);
	
	//Verify bad request and missing header
//    Assert.assertEquals(400, response.getStatusCodeValue());
//    Assert.assertEquals(true, response.getBody().contains("Missing request header"));
	
	private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
