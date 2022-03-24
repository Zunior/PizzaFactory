package com.example.PizzaHut.modules.pizza.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class PizzaDto {

  private String name;
  private String slug;
  private int size;
  private int price;
  private LocalDateTime date;

  public PizzaDto() {

  }
  
  public static class Builder {
	  
  }

  public PizzaDto(String name, String slug, int size, int price, LocalDateTime date) {
    super();
    this.name = name;
    this.slug = slug;
    this.size = size;
    this.price = price;
    this.date = date;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getSlug() {
    return slug;
  }
  public void setSlug(String slug) {
    this.slug = slug;
  }
  public int getSize() {
    return size;
  }
  public void setSize(int size) {
    this.size = size;
  }
  public int getPrice() {
    return price;
  }
  public void setPrice(int price) {
    this.price = price;
  }
  public LocalDateTime getDate() {
    return date;
  }
  public void setDate(LocalDateTime date) {
    this.date = date;
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
