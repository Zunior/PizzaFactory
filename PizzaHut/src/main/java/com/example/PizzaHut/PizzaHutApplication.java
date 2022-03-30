package com.example.PizzaHut;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.example.PizzaHut.modules.pizza.dto.PizzaDto;
import com.example.PizzaHut.modules.pizza.service.GenericService;

@SpringBootApplication
@EnableCaching
public class PizzaHutApplication {

  @Autowired
  private static GenericService<PizzaDto> pizzaService;

  public PizzaHutApplication(GenericService<PizzaDto> pizzaService) {
    PizzaHutApplication.pizzaService = pizzaService;
  }

  public static void main(String[] args) {
    SpringApplication.run(PizzaHutApplication.class, args);
    
    pizzaService.add(PizzaDto.builder("capricciosa").name("Capricciosa").size(240).price(20).build());
    pizzaService.add(PizzaDto.builder("calzone").name("Calzone").size(280).price(23).build());
  }

}
