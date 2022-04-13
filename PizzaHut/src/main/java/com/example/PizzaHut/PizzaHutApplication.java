package com.example.PizzaHut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PizzaHutApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaHutApplication.class, args);
	}

}
