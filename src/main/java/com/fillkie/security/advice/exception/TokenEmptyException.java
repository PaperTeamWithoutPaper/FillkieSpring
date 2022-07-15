package com.fillkie.security.advice.exception;

public class TokenEmptyException extends IllegalArgumentException {

  public TokenEmptyException(String msg, Throwable t) {
    super(msg, t);
  }

  public TokenEmptyException(String msg) {
    super(msg);
  }

  public TokenEmptyException() {
    super();
  }

}
