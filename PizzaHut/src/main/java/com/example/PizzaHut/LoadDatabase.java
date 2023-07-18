package com.example.PizzaHut;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.PizzaHut.modules.pizza.dto.PizzaDto;
import com.example.PizzaHut.modules.pizza.service.GenericService;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.*;

@Configuration
public class LoadDatabase {

	private static final Logger log = LogManager.getLogger(LoadDatabase.class.getName());
//	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	private static GenericService<PizzaDto> pizzaService;
	
	@Autowired
	public LoadDatabase(GenericService<PizzaDto> pizzaService) {
		LoadDatabase.pizzaService = pizzaService;
	}
	
	@Bean
	CommandLineRunner initDatabase() {
		return args -> {
			log.debug("Preloading " + pizzaService.add(PizzaDto.builder("capricciosa").name("Capricciosa").size(240).price(20).build()));
			log.debug("Preloading " + pizzaService.add(PizzaDto.builder("calzone").name("Calzone").size(280).price(23).build()));
		};
	}
	
	

}
