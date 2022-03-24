package com.example.PizzaHut.modules.pizza.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = PizzaDto.Builder.class)
public class PizzaDto {

  private final String name;
  private final String slug;
  private final int size;
  private final int price;
  private final LocalDateTime date;

  @JsonPOJOBuilder(withPrefix = "") // , buildMethodName = "create" - if build() method has create name
  public static class Builder {
	  
	  private final String slug;
	  
	  private String name = "";
	  private int size = 0;
	  private int price = 0;
	  private LocalDateTime date = LocalDateTime.now();
	  
	  public Builder(String slug) {
		  this.slug = slug;
	  }
	  
	  public Builder name(String name) { 
		  this.name = name;      
		  return this; 
      }
	  
	  public Builder size(int size) { 
		  this.size = size;      
		  return this; 
      }
	  
	  public Builder price(int price) { 
		  this.price = price;      
		  return this; 
      }
	  
	  public PizzaDto build() {
		  return new PizzaDto(this);
	  }
  }

  public String getName() {
    return name;
  }
  public String getSlug() {
    return slug;
  }
  public int getSize() {
    return size;
  }
  public int getPrice() {
    return price;
  }
  public LocalDateTime getDate() {
    return date;
  }

  private PizzaDto(Builder builder) {
	  this.slug = builder.slug;
	  this.name = builder.name;
	  this.size = builder.size;
	  this.price = builder.price;
	  this.date = builder.date;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(date, name, slug);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    PizzaDto other = (PizzaDto) obj;
    return Objects.equals(date, other.date) && Objects.equals(name, other.name) && Objects.equals(slug, other.slug);
  }

}
