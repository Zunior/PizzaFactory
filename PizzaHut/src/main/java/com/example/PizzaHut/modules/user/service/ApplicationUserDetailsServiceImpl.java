package com.example.PizzaHut.modules.user.service;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.PizzaHut.modules.user.model.ApplicationUser;
import com.example.PizzaHut.modules.user.repository.ApplicationUserRepository;

@Service
public class ApplicationUserDetailsServiceImpl implements UserDetailsService {

  private final ApplicationUserRepository applicationUserRepository;

  @Autowired
  public ApplicationUserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
	  this.applicationUserRepository = applicationUserRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	  ApplicationUser applicationUser = applicationUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
	
	  return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
  }

}
