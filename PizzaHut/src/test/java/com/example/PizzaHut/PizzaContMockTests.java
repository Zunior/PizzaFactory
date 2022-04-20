package com.example.PizzaHut;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import org.junit.Assert;
//import org.skyscreamer.jsonassert.JSONAssert;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import com.example.PizzaHut.modules.pizza.controller.PizzaController;
import com.example.PizzaHut.modules.pizza.dto.PizzaDto;
import com.example.PizzaHut.modules.pizza.model.Pizza;
import com.example.PizzaHut.modules.pizza.repository.PizzaRepository;
import com.example.PizzaHut.modules.pizza.service.PizzaServiceImpl;
import com.example.PizzaHut.modules.user.model.ApplicationUser;
import com.example.PizzaHut.modules.user.repository.ApplicationUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebMvcTest(PizzaController.class)
@AutoConfigureMockMvc
public class PizzaContMockTests {
	
	Logger logger = LoggerFactory.getLogger(PizzaContMockTests.class);
	
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	private WebApplicationContext context;
	private UserDetailsService userDetailsService;
	private ApplicationUserRepository applicationUserRepository;
    
    @MockBean
	Clock clock;
    
    @Autowired
    private MockMvc mockMvc;
    
    @InjectMocks
    private PizzaServiceImpl pizzaService;
    
    @MockBean
    PizzaRepository mockPizzaRepo;
    
    @LocalServerPort
    private int port;
    
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    	this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    	this.authenticationManager = authenticationManager;
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
    
//    @PostConstruct
//    @Before
    public void addUser() {
    	ApplicationUser user = new ApplicationUser("testUser", passwordEncoder.encode("testUser"));
    	applicationUserRepository.save(user);
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
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	
	@Test
	public void testGetByIdMockMvcNoAuthOutside() throws Exception {
		Pizza returnedPizza = new Pizza("Capricciosa", "capricciosa", 240, 20, LocalDateTime.now());
		when(mockPizzaRepo.findBySlug("capricciosa")).thenReturn(Optional.of(returnedPizza));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/pizzaFactory/slug?slug=capricciosa")
		        .accept(MediaType.APPLICATION_JSON);
		        
		MvcResult result = mockMvc.perform(request)
		        .andExpect(status().isForbidden())
		        .andReturn();

	}
	
	@WithUserDetails("testUser")
	@Test
	public void testGetByIdMockMvcOutside() throws Exception {
		Pizza returnedPizza = new Pizza("Capricciosa", "capricciosa", 240, 20, LocalDateTime.now());
		when(mockPizzaRepo.findBySlug("capricciosa")).thenReturn(Optional.of(returnedPizza));
//		when(mockPizzaService.get("capricciosa")).thenReturn(PizzaDto.builder("capricciosa").name("Capricciosa").size(240).price(20).build());
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/pizzaFactory/slug?slug=capricciosa")
		        .accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request)
		        .andExpect(status().isOk())
				.andExpect(content().json("{slug:capricciosa,size:240,price:20}"))
				.andReturn();
		
	}
	
//	@Test
	public void testGetByIdRestTemplate() {
		HttpEntity<PizzaDto> entity = new HttpEntity<PizzaDto>(null, headers);
		
//		ResponseEntity<String> response = restTemplate.exchange(
//		          createURLWithPort("/pizzaFactory/capricciosa"), HttpMethod.GET, entity, String.class);
		ResponseEntity<String> response = restTemplate.withBasicAuth("testUser", SecurityContextHolder.getContext().getAuthentication().toString())
	            .getForEntity("/private/hello", String.class);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
		
		String expected = "{\"slug\":\"capricciosa\",\"name\":\"Capricciosa\",\"size\":240,\"price\":20}";
//		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertEquals(expected, response.getBody());
		
		// Verify bad request and missing header
	    assertEquals(400, response.getStatusCodeValue());
	    assertEquals(true, response.getBody().contains("Missing request header"));
	}
	

	
	private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
