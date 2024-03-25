//package com.springboot.email.config;
//
//import java.util.concurrent.Executor;
//import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//@Configuration
//@EnableAsync
//public class AsyncConfig implements AsyncConfigurer {
//
//  @Bean(name = "taskExecutor")
//  public Executor taskExecutor() {
//    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//    executor.setCorePoolSize(5);
//    executor.setMaxPoolSize(10);
//    executor.setQueueCapacity(25);
//    executor.initialize();
//    return executor;
//  }
//
//  @Override
//  public Executor getAsyncExecutor() {
//    return taskExecutor();
//  }
//
//  @Override
//  @Bean
//  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//    return new CustomAsyncExceptionHandler(); // Implement your custom exception handler if needed
//  }
//}
//
