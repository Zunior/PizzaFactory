package com.example.PizzaHut.modules.pizza.controller;

import com.example.PizzaHut.exception.PizzaExistsException;
import com.example.PizzaHut.modules.pizza.dto.PizzaDto;
import com.example.PizzaHut.modules.pizza.service.GenericService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Api(tags = "Clients")
@RequestMapping({ "/pizzaFactory" })
public class PizzaController {

	private static final Logger log = LogManager.getLogger(PizzaController.class.getName());

	private final GenericService<PizzaDto> pizzaService;

	@Autowired
	public PizzaController(GenericService<PizzaDto> pizzaService) {
		this.pizzaService = pizzaService;
	}

	/**
	 * @return
	 */
	@ApiOperation(value = "This method is used to get pizzas.")
	@GetMapping("/pizzas")
	@ResponseStatus(HttpStatus.OK)
	List<PizzaDto> findAll() {
		return pizzaService.getAll();
	}

	/**
	 * @param slug
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{slug}")
	@ResponseStatus(HttpStatus.OK)
	PizzaDto load(@RequestParam("slug") String slug) throws Exception {
		return pizzaService.get(slug);
	}

	/**
	 * @param name
	 * @return
	 */
	@GetMapping("/searchByName")
	@ResponseStatus(HttpStatus.OK)
	List<PizzaDto> searchByName(@RequestParam("name") String name) {
		return pizzaService.searchByName(name);
	}

	/**
	 * @param pizzaDto
	 * @return
	 * @throws PizzaExistsException
	 */
	@PostMapping("/save")
	PizzaDto save(@Valid @RequestBody PizzaDto pizzaDto) throws PizzaExistsException {
		return pizzaService.add(pizzaDto);
	}

	/**
	 * @param pizzaDto
	 * @throws PizzaExistsException
	 */
	@PostMapping("/update")
	void update(@Valid @RequestBody PizzaDto pizzaDto) throws PizzaExistsException {
		pizzaService.update(pizzaDto);
	}

	/**
	 * @param pizzaDto
	 * @param slug
	 * @throws PizzaExistsException
	 */
	@PutMapping("/replace/{slug}")
	void replace(@Valid @RequestBody PizzaDto pizzaDto, @PathVariable String slug) throws PizzaExistsException {
		pizzaService.replace(pizzaDto, slug);
	}

	/**
	 * @param slug
	 */
	@DeleteMapping("/{slug}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	void delete(@RequestParam("slug") String slug) {
		try {
			pizzaService.delete(slug);
		} catch (DataIntegrityViolationException ex) {
			throw new PersistenceException("Item can not be deleted", ex);
		}
	}

	// just as example, it is not used
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public String return404(NoSuchElementException ex) {
		log.error("Unable to complete transaction", ex);
		return ex.getMessage();
	}

}
