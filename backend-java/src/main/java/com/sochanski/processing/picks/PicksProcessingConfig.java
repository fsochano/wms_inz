package com.sochanski.processing.picks;

import com.sochanski.container.ContainerRepository;
import com.sochanski.location.LocationRepository;
import com.sochanski.order.OrderHeaderRepository;
import com.sochanski.order.OrderLineRepository;
import com.sochanski.pick.PickListRepository;
import com.sochanski.pick.PickTaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PicksProcessingConfig {

    @Bean
    public PicksGenerationTask.PicksGenerationTaskBuilder picksGenerationTaskBuilder(
            OrderHeaderRepository orderHeaderRepository,
            OrderLineRepository orderLineRepository,
            PickListRepository pickListRepository,
            PickTaskRepository pickTaskRepository,
            ContainerRepository containerRepository,
            LocationRepository locationRepository) {
        return PicksGenerationTask.builder()
                .orderHeaderRepository(orderHeaderRepository)
                .orderLineRepository(orderLineRepository)
                .pickListRepository(pickListRepository)
                .pickTaskRepository(pickTaskRepository)
                .containerRepository(containerRepository)
                .locationRepository(locationRepository);
    }
}
