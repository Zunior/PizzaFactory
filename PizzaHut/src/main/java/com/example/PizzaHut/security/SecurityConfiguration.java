package com.example.PizzaHut.security;

import static com.example.PizzaHut.util.SecurityConstants.SIGN_UP_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.PizzaHut.modules.user.service.ApplicationUserDetailsServiceImpl;
import com.example.PizzaHut.security.filter.AuthenticationFilter;
import com.example.PizzaHut.security.filter.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private ApplicationUserDetailsServiceImpl userDetailsService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  // public SecurityConfiguration(ApplicationUserDetailsServiceImpl
  // userDetailsService, BCryptPasswordEncoder passwordEncoder) {
  // this.userDetailsService = userDetailsService;
  // this.passwordEncoder = passwordEncoder;
  // }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll().anyRequest().authenticated().and().addFilter(new AuthenticationFilter(authenticationManager())).addFilter(new AuthorizationFilter(authenticationManager())).sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/pizzaFactory/pizzas/**", "/h2-console/**", "/swagger-ui.html/**");
  }

}
