package com.example.PizzaHut.exception;

import com.example.PizzaHut.util.ExceptionConstants;

public class PizzaNotExistsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public PizzaNotExistsException() {
    super(ExceptionConstants.PIZZA_NOT_EXIST);
  }

  public static String getExceptionMessage() {
    return new PizzaNotExistsException().getMessage();
  }
}
