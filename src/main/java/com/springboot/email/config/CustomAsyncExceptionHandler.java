package com.springboot.email.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import java.lang.reflect.Method;

public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

  @Override
  public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
    System.out.println("Async method: " + method.getName() + " threw an exception.");
    System.out.println("Exception message: " + throwable.getMessage());
    // You can add more custom handling logic here, such as logging the exception or notifying administrators.
  }
}

