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

    pizzaService.add(new PizzaDto("Capricciosa", "capricciosa", 240, 23, null));
    pizzaService.add(new PizzaDto("Calzone", "calzone", 280, 23, null));
  }

}
