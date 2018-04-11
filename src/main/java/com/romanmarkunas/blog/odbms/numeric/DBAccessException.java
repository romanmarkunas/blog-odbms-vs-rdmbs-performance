package com.romanmarkunas.blog.odbms.numeric;

public class DBAccessException extends Exception {

    public DBAccessException(String s) {
        super(s);
    }

    public DBAccessException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
