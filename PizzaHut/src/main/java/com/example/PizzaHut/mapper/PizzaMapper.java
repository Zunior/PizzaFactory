package com.example.PizzaHut.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.PizzaHut.modules.pizza.dto.PizzaDto;
import com.example.PizzaHut.modules.pizza.model.Pizza;

// Automatic bean creation doesnt work ???
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PizzaMapper {

  @Mapping(target = "pizzaDto", source = "pizza")
  PizzaDto toDto(Pizza pizza);

  @Mapping(target = "pizza", source = "pizzaDto")
  Pizza toEntity(PizzaDto pizzaDto);

}
