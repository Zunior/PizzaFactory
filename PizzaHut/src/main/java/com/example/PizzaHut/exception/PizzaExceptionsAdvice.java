package com.example.PizzaHut.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PizzaExceptionsAdvice {

  @ResponseBody
  @ExceptionHandler(PizzaExistsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String PizzaExceptionsAdvice(PizzaExistsException ex) {
    return ex.getExceptionMessage();
  }

  @ResponseBody
  @ExceptionHandler(PizzaNotExistsException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String PizzaExceptionsAdvice(PizzaNotExistsException ex) {
    return ex.getExceptionMessage();
  }

}
