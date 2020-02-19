package com.sochanski.processing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
//@EnableScheduling
public class ProcessingConfiguration {

    @Bean
    public ScheduledThreadPoolExecutor scheduledThreadPoolExecutor() {
        return new ScheduledThreadPoolExecutor(10);
    }

    @Bean
    public ConcurrentTaskScheduler concurrentTaskScheduler() {
        return new ConcurrentTaskScheduler(scheduledThreadPoolExecutor());
    }

    @Bean
    public TransactionProcessingService processingService(PlatformTransactionManager txManager) {
        return new TransactionProcessingService(concurrentTaskScheduler(), txManager);
    }

}
