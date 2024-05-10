package com.qualgo.kien.application.exception;

public class ForbiddenException extends RuntimeException {
  public ForbiddenException(){
    super("Accessing to this resource is forbidden");
  }
}
