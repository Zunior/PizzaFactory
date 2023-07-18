package com.example.PizzaHut.modules.pizza.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "hiddenBuilder")
@EqualsAndHashCode(exclude = {"date"})
public class PizzaDto {

	private String name;
	private String slug;
	private int size;
	private int price;
	private LocalDateTime date;

	// put required variable to builder constructor
	public static PizzaDtoBuilder builder(String slug) {
		return hiddenBuilder().slug(slug).date(LocalDateTime.now());
	}

//  public static class PizzaDtoBuilder {
//      private PizzaDtoBuilder client(String slug) { return this; }
//  }
	
	

}
