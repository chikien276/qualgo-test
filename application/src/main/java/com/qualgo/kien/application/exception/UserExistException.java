package com.qualgo.kien.application.exception;

public class UserExistException extends BaseException {
  public UserExistException(String username) {
    super("User " + username + " is already exist");
  }

  @Override
  public int getErrorCode() {
    return 1;
  }
}
