package com.fillkie.advice.exception;

public class TokenEmptyException extends RuntimeException {

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
