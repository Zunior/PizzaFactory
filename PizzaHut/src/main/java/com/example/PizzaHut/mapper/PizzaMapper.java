package com.example.PizzaHut.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.PizzaHut.modules.pizza.dto.PizzaDto;
import com.example.PizzaHut.modules.pizza.model.Pizza;

// Automatic bean creation doesnt work ???
//@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Mapper(componentModel = "spring")
public interface PizzaMapper {
	
	PizzaMapper INSTANCE = Mappers.getMapper(PizzaMapper.class);
	
	PizzaMapper pizzaToDto(Pizza pizza);
	Pizza pizzaToEntity(PizzaDto pizzaDto);

//	@Mapping(target = "pizzaDto", source = "pizza")
//	PizzaDto toDto(Pizza pizza);
//
//	@Mapping(target = "pizza", source = "pizzaDto")
//	Pizza toEntity(PizzaDto pizzaDto);

}
