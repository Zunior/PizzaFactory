package com.example.PizzaHut.modules.pizza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.PizzaHut.modules.pizza.dto.PizzaDto;
import com.example.PizzaHut.modules.pizza.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, String> {

	Optional<Pizza> findBySlug(String slug);

	void deleteBySlug(String slug);

	@Query("SELECT t FROM #{#entityName} t WHERE lower(t.name) LIKE lower(concat('%', ?1, '%'))")
	List<Pizza> searchByName(final String partialName);

	@Modifying
	@Query("UPDATE #{#entityName} t set t.name =:#{#pizza.name}, t.price =:#{#pizza.price}, t.size =:#{#pizza.size} where t.slug = :slug")
	void updateBySlug(@Param("pizza") PizzaDto pizza, @Param("slug") String slug);

}
