package org.example.loom.support;

import org.example.loom.support.decorator.InheritThreadLocalDecorator;
import org.springframework.boot.task.SimpleAsyncTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

@Configuration
public class AsyncTaskConfiguration {
    @Bean
    @Primary
    AsyncTaskExecutor applicationTaskExecutorVirtualThreads(SimpleAsyncTaskExecutorBuilder builder) {
        var executor = builder.taskDecorator(InheritThreadLocalDecorator::new).build();
        return new DelegatingSecurityContextAsyncTaskExecutor(executor);
    }
}
