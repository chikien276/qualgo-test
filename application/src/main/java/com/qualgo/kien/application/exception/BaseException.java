package com.qualgo.kien.application.exception;

import lombok.RequiredArgsConstructor;

public abstract class BaseException extends Exception {
    public abstract int getErrorCode();

    public BaseException(String message) {
        super(message);
    }

    public BaseException(){}

    public BaseException(Throwable cause) {
        super(cause);
    }
}
