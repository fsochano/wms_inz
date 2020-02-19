package com.sochanski.processing;

import com.sochanski.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;


public class TransactionProcessingService {

    private final AsyncTaskExecutor executorService;
    private final PlatformTransactionManager txManager;

    public TransactionProcessingService(AsyncTaskExecutor executorService, PlatformTransactionManager txManager) {
        this.executorService = executorService;
        this.txManager = txManager;
    }

    public <T> Future<T> process(TransactionCallback<T> callback) {
        return executorService.submit(new Task<T>(callback, new TransactionTemplate(txManager)));
    }

    private static class Task<T> implements Callable<T> {
        private static final Logger log = LoggerFactory.getLogger(Task.class);

        private final TransactionCallback<T> task;
        private final TransactionTemplate txTemplate;

        private Task(TransactionCallback<T> task, TransactionTemplate txTemplate) {
            this.task = task;
            this.txTemplate = txTemplate;
        }


        @Override
        public T call() throws Exception {
            try {
                return SecurityUtils.executeAsSystem(() -> txTemplate.execute(task));
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
                throw e;
            }
        }
    }
}
