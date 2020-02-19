package com.sochanski.processing.picks;

import com.sochanski.processing.picks.PicksGenerationTask.PicksGenerationTaskBuilder;
import com.sochanski.processing.TransactionProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class PicksGenerationService {

    private final TransactionProcessingService transactionProcessingService;
    private final PicksGenerationTaskBuilder picksGenerationTaskBuilder;

    @Autowired
    public PicksGenerationService(TransactionProcessingService transactionProcessingService,
                                  PicksGenerationTaskBuilder picksGenerationTaskBuilder) {
        this.transactionProcessingService = transactionProcessingService;
        this.picksGenerationTaskBuilder = picksGenerationTaskBuilder;
    }

    public Future<?> schedulePicksGeneration(long orderHeaderId) {
        return transactionProcessingService.process(picksGenerationTaskBuilder.orderHeaderId(orderHeaderId).build());
    }
}
