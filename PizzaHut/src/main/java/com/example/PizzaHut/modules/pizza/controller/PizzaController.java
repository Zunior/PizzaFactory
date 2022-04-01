package com.example.PizzaHut.modules.pizza.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.PizzaHut.exception.PizzaExistsException;
import com.example.PizzaHut.modules.pizza.dto.PizzaDto;
import com.example.PizzaHut.modules.pizza.service.GenericService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Clients")
@RequestMapping({ "/pizzaFactory" })
public class PizzaController {

	private final GenericService<PizzaDto> pizzaService;

	@Autowired
	public PizzaController(GenericService<PizzaDto> pizzaService) {
		this.pizzaService = pizzaService;
	}

	@ApiOperation(value = "This method is used to get pizzas.")
	@GetMapping("/pizzas")
	@ResponseStatus(HttpStatus.OK)
	List<PizzaDto> findAll() {

		return pizzaService.getAll();
	}

	@GetMapping("/{slug}")
	@ResponseStatus(HttpStatus.OK)
	PizzaDto load(@RequestParam("slug") String slug) throws Exception {
		return pizzaService.get(slug);
	}

	@GetMapping("/searchByName")
	@ResponseStatus(HttpStatus.OK)
	List<PizzaDto> searchByName(@RequestParam("name") String name) {
		return pizzaService.searchByName(name);
	}

	@PostMapping("/save")
	PizzaDto save(@Valid @RequestBody PizzaDto pizzaDto) throws PizzaExistsException {
		return pizzaService.add(pizzaDto);
	}

	@PostMapping("/update")
	void update(@Valid @RequestBody PizzaDto pizzaDto) throws PizzaExistsException {
		pizzaService.update(pizzaDto);
	}

	@DeleteMapping("/{slug}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	void delete(@RequestParam("slug") String slug) {
		try {
			pizzaService.delete(slug);
		} catch (DataIntegrityViolationException ex) {
			throw new PersistenceException("Item can not be deleted", ex);
		}
	}

}
