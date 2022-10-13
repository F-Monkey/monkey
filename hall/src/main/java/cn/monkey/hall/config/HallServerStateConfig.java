package cn.monkey.hall.config;

import cn.monkey.commons.utils.Timer;
import cn.monkey.commons.data.repository.ServerRepository;
import cn.monkey.hall.state.HallServerStateGroupFactory;
import cn.monkey.state.core.SimpleStateGroupPool;
import cn.monkey.state.core.StateGroupFactory;
import cn.monkey.state.core.StateGroupPool;
import cn.monkey.state.scheduler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HallServerStateConfig {

    @Bean
    StateGroupFactory hallServerStateGroupFactory(Timer timer,
                                                  ServerRepository roomServerRepository) {
        return new HallServerStateGroupFactory(timer, roomServerRepository);
    }

    @Bean
    StateGroupPool stateGroupPool(StateGroupFactory stateGroupFactory) {
        return new SimpleStateGroupPool(stateGroupFactory);
    }

    @Bean
    StateGroupSchedulerFactory stateGroupSchedulerFactory(StateGroupSchedulerFactoryConfig stateGroupFactoryConfig) {
        return new SimpleStateGroupSchedulerFactory(stateGroupFactoryConfig);
    }

    @Bean
    EventPublishSchedulerFactory eventPublishSchedulerFactory() {
        return new SimpleEventPublishSchedulerFactory();
    }

    @Bean
    SchedulerManager schedulerManager(StateGroupPool stateGroupPool,
                                      StateGroupSchedulerFactory stateGroupSchedulerFactory,
                                      EventPublishSchedulerFactory eventPublishSchedulerFactory,
                                      SchedulerManagerConfig schedulerManagerConfig) {
        return new SimpleSchedulerManager(stateGroupPool, stateGroupSchedulerFactory, eventPublishSchedulerFactory, schedulerManagerConfig);
    }
}
