package com.example.PizzaHut.modules.pizza.service;

import java.util.List;

import com.example.PizzaHut.modules.pizza.dto.PizzaDto;

public interface GenericService<T> {
	public List<T> getAll();

	public T get(String id) throws Exception;

	public List<T> searchByName(String name);

	public T add(T t);

	public void update(T t);
	
	public PizzaDto replace(PizzaDto pizzaDto, String slug);

	public void delete(String id);
}
