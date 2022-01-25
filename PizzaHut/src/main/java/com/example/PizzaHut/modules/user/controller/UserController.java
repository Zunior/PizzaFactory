package com.example.PizzaHut.modules.user.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PizzaHut.modules.user.model.ApplicationUser;
import com.example.PizzaHut.modules.user.repository.ApplicationUserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

  private ApplicationUserRepository applicationUserRepository;
  private PasswordEncoder passwordEncoder;

  public UserController(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder passwordEncoder) {
    this.applicationUserRepository = applicationUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/record")
  public void signUp(@RequestBody ApplicationUser user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    applicationUserRepository.save(user);
  }

}
