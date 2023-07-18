package com.example.PizzaHut.modules.pizza.service;

import java.util.List;

public interface GenericService<T> {
	List<T> getAll();

	T get(String id) throws Exception;

	List<T> searchByName(String name);

	T add(T t);

	void update(T t);
	
	T replace(T t, String slug);

	void delete(String id);
}
