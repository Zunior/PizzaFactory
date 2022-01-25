package com.example.PizzaHut.modules.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "APP_USER")
public class ApplicationUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "AU_ID", nullable = false)
  private long id;
  @NotNull
  @NotBlank(message = "This field is required")
  @Column(name = "AU_LOGIN", nullable = false, unique = true)
  private String username;
  @Column(name = "AU_PASS")
  private String password;

  public ApplicationUser() {
    super();
  }

  public ApplicationUser(long id, @NotNull @NotBlank(message = "This field is required") String username, String password) {
    super();
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
