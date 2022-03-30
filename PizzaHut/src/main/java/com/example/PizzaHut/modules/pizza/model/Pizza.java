package com.example.PizzaHut.modules.pizza.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "PIZZA")
public class Pizza {

  @Id
  @NotNull
  @NotBlank(message = "This field is required")
  @Pattern(regexp = "^[a-z]+$", message = "Invalid slug format")
  @Column(name = "PI_SLUG", nullable = false, length = 30)
  private String slug;
  @NotNull
  @NotBlank(message = "This field is required")
  @Column(name = "PI_NAME", nullable = false, length = 255)
  private String name;
  @NotNull(message = "This field is required")
  @Column(name = "PI_SIZE", nullable = false)
  private int size;
  @NotNull(message = "This field is required")
  @DecimalMin(value = "0.01", message = "Minimal price is 0.01")
  @Column(name = "PI_PRICE", nullable = false)
  private int price;
  @Column(name = "PI_DATE", nullable = false)
  private LocalDateTime date;

  public Pizza() {

  }

  public Pizza(String name, String slug, int size, int price, LocalDateTime date) {
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
    Pizza other = (Pizza) obj;
    return Objects.equals(date, other.date) && Objects.equals(name, other.name) && Objects.equals(slug, other.slug);
  }

}
