package com.example.PizzaHut.modules.pizza.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.PizzaHut.exception.PizzaExistsException;
import com.example.PizzaHut.exception.PizzaNotExistsException;
import com.example.PizzaHut.modules.pizza.dto.PizzaDto;
import com.example.PizzaHut.modules.pizza.model.Pizza;
import com.example.PizzaHut.modules.pizza.repository.PizzaRepository;

@Service
@Transactional
public class PizzaServiceImpl implements GenericService<PizzaDto> {

<<<<<<< HEAD
	private final PizzaRepository pizzaRepo;
	private final ModelMapper modelMapper;
	// @Autowired
	// private PizzaMapper pizzaMapper;
=======
  private final PizzaRepository pizzaRepo;
  private final ModelMapper modelMapper;
  //@Autowired
 // private PizzaMapper pizzaMapper;
  
  public PizzaServiceImpl(PizzaRepository pizzaRepo, ModelMapper modelMapper) {
	  this.pizzaRepo = pizzaRepo;
	  this.modelMapper = modelMapper;
  }
>>>>>>> refs/remotes/origin/Junit

	@Autowired
	public PizzaServiceImpl(PizzaRepository pizzaRepo, ModelMapper modelMapper) {
		this.pizzaRepo = pizzaRepo;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PizzaDto> getAll() {
		List<PizzaDto> pizzas = new ArrayList<PizzaDto>();
		pizzaRepo.findAll().forEach(pizza -> {
			pizzas.add(convertToDto(pizza));
		});
		return pizzas.stream().sorted(Comparator.comparing(PizzaDto::getDate).reversed()).collect(Collectors.toList());
	}

	@Override
	public PizzaDto get(String slug) {
		Pizza pizza = pizzaRepo.findBySlug(slug).orElseThrow(() -> new PizzaNotExistsException());
		return convertToDto(pizza);
	}

	@Override
	public List<PizzaDto> searchByName(final String partialName) {
		return pizzaRepo.searchByName(partialName).stream().map(pizza -> convertToDto(pizza))
				.collect(Collectors.toList());
	}

	@Override
	@CacheEvict(value = "addPizza", allEntries = true)
	public PizzaDto add(PizzaDto pizzaDto) {
		Optional<Pizza> existingPizza = pizzaRepo.findBySlug(pizzaDto.getSlug());
		if (existingPizza.isPresent()) {
			throw new PizzaExistsException();
		}

		return convertToDto(pizzaRepo.save(convertToEntity(pizzaDto)));
	}

	@Override
	@CacheEvict(value = "updatePizza", allEntries = true)
	public void update(PizzaDto pizzaDto) {
		Optional<Pizza> existingPizza = pizzaRepo.findBySlug(pizzaDto.getSlug());
		if (!existingPizza.isPresent()) {
			throw new PizzaNotExistsException();
		}
		pizzaRepo.updateBySlug(pizzaDto, existingPizza.get().getSlug());
	}

	@Override
	@CacheEvict(value = "deletePizza", allEntries = true)
	public void delete(String slug) {
		pizzaRepo.deleteBySlug(slug);

	}

	private Pizza convertToEntity(PizzaDto pizzaDto) {
		return modelMapper.map(pizzaDto, Pizza.class);
	}

	private PizzaDto convertToDto(Pizza pizza) {
		return pizza == null ? null
				: PizzaDto.builder(pizza.getSlug()).name(pizza.getName()).size(pizza.getSize()).price(pizza.getPrice())
						.build();
	}

}
