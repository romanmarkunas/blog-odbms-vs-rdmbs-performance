package com.romanmarkunas.blog.odbms.common;

@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws Exception;
}
