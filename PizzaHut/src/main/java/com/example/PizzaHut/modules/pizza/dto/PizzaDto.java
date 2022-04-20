package com.example.PizzaHut.modules.pizza.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "hiddenBuilder")
@EqualsAndHashCode(exclude = {"date"})
@ToString
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
