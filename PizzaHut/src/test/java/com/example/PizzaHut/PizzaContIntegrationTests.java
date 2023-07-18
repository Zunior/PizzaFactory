package com.example.PizzaHut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import org.junit.Assert;
//import org.skyscreamer.jsonassert.JSONAssert;

import java.time.Clock;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import org.junit.Before;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.PizzaHut.modules.pizza.dto.PizzaDto;
import com.example.PizzaHut.modules.pizza.service.PizzaServiceImpl;
import com.example.PizzaHut.modules.user.model.ApplicationUser;
import com.example.PizzaHut.modules.user.repository.ApplicationUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebMvcTest(PizzaController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = {"classpath:test-configuration.properties"})
public class PizzaContIntegrationTests {
	
	Logger logger = LoggerFactory.getLogger(PizzaContIntegrationTests.class);
	
	private PizzaServiceImpl pizzaService;
	private PasswordEncoder passwordEncoder;
	private WebApplicationContext context;
	private UserDetailsService userDetailsService;
	private ApplicationUserRepository applicationUserRepository;
	TestRestTemplate restTemplate;
    
    @MockBean
	Clock clock;
    
    @Autowired
    private MockMvc mockMvc;
    
    @LocalServerPort
    private int port;
    
    @Autowired
    public void setPizzaService(PizzaServiceImpl pizzaService) {
    	this.pizzaService = pizzaService;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    	this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setApplicationContext(WebApplicationContext context) {
    	this.context = context;
    }
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
    	this.userDetailsService = userDetailsService;
    }
    @Autowired
    public void setApplicationUserRepository(ApplicationUserRepository applicationUserRepository) {
    	this.applicationUserRepository = applicationUserRepository;
    }
    @Autowired
    public void setRestTemplate(TestRestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
    
//    @PostConstruct
//    @Before
    public void addUser() {
    	ApplicationUser user = new ApplicationUser("testUser", passwordEncoder.encode("testUser"));
    	applicationUserRepository.save(user);
    	Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
    	SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
	public void loginUser() {
    	UserDetails user = userDetailsService.loadUserByUsername("testUser");
    	Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
    	SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    @Before
    public void setup() {
    	mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(springSecurity())
          .build();
    }
	
	@Test
//	@WithUserDetails("testUser")
	public void testGetByIdInside() {
		String slug = "capricciosa";
		PizzaDto pizza = pizzaService.get(slug);
		
		assertEquals(PizzaDto.builder("capricciosa").name("Capricciosa").size(240).price(20).build(), pizza);
	}
	
	@Test
	public void testGetByIdMockMvcNoAuthOutside() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.get("/pizzaFactory/slug?slug=capricciosa")
//					.param("slug", "capricciosa")
		        .accept(MediaType.APPLICATION_JSON);
		        
		mockMvc.perform(request)
		        .andExpect(status().isForbidden());

	}
	
	@WithUserDetails("testUser")
	@Test
	public void testGetByIdMockMvcOutside() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/pizzaFactory/slug?slug=capricciosa")
		        .accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		        .andExpect(status().isOk())
		        .andDo(print())
//			        .andExpect(content().string(containsString("testUser")));
		        .andExpect(MockMvcResultMatchers.jsonPath("$.slug").value("capricciosa"))
		        .andExpect(content().json("{slug:capricciosa,size:240,price:20}"));

	}
	
//	@WithUserDetails("testUser")
//	@Test
	public void testGetByIdRestTemplate() {
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication(); 
//		accessToken = auth.getTokenValue();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		String userAuth = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
		headers.set("Authorization", "Bearer " + userAuth);
//	    headers.setBearerAuth(userAuth);
		HttpEntity<String> entity = new HttpEntity<>(headers);
	    
		
		ResponseEntity<PizzaDto> response = restTemplate.exchange(
		          "/pizzaFactory/slug?slug=capricciosa", HttpMethod.GET, entity, PizzaDto.class);
//		ResponseEntity<String> response = restTemplate
////				.withBasicAuth("testUser", SecurityContextHolder.getContext().getAuthentication().getCredentials().toString())
//	            .getForEntity("/pizzaFactory/slug?slug=capricciosa", String.class);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
		
		String expected = "{\"slug\":\"capricciosa\",\"name\":\"Capricciosa\",\"size\":240,\"price\":20}";
//		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertEquals(expected, Objects.requireNonNull(response.getBody()).toString());
		
		// Verify bad request and missing header
	    assertEquals(400, response.getStatusCodeValue());
//	    assertEquals(true, response.getBody().contains("Missing request header"));
	}
	

	
	private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
