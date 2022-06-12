package com.hemou.generator.exception;

public class CodeGenException extends RuntimeException {

    public CodeGenException(String message, Object... params) {
        super(String.format(message, params));
    }

    public CodeGenException(String message, Throwable cause, Object... params) {
        super(String.format(message, params), cause);
    }

    public CodeGenException(Throwable cause) {
        super(cause);
    }
}
