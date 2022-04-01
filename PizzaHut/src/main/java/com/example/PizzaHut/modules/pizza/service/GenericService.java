package com.example.PizzaHut.modules.pizza.service;

import java.util.List;

public interface GenericService<T> {
	public List<T> getAll();

	public T get(String id) throws Exception;

	public List<T> searchByName(String name);

	public T add(T t);

	public void update(T t);

	public void delete(String id);
}
