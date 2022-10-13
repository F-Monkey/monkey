package cn.monkey.game.config;

import cn.monkey.commons.utils.Timer;
import cn.monkey.game.data.UserCmdPair;
import cn.monkey.game.event.HallServerBroadCastHandler;
import cn.monkey.game.repository.GameConfigRepository;
import cn.monkey.game.state.core.GameStateGroupFactory;
import cn.monkey.game.state.scheduler.GameStateGroupSchedulerManager;
import cn.monkey.state.core.SimpleStateGroupPool;
import cn.monkey.state.core.StateGroupFactory;
import cn.monkey.state.core.StateGroupPool;
import cn.monkey.state.scheduler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StateAutoConfig {

    @Bean
    Timer timer() {
        return new Timer() {
        };
    }

    @Bean
    StateGroupSchedulerFactoryConfig stateGroupSchedulerFactoryConfig() {
        return StateGroupSchedulerFactoryConfig.newBuilder().build();
    }

    @Bean
    SchedulerManagerConfig schedulerManagerConfig() {
        return SchedulerManagerConfig.newBuilder().build();
    }

    @Bean
    HallServerBroadCastHandler hallServerBroadCastHandler() {
        return new HallServerBroadCastHandler(1024, 2);
    }

    @Bean
    StateGroupFactory stateGroupFactory(Timer timer, GameConfigRepository repository,
                                                     HallServerBroadCastHandler serverBroadCastHandler) {
        return new GameStateGroupFactory(timer, repository, serverBroadCastHandler);
    }

    @Bean
    StateGroupPool stateGroupPool(StateGroupFactory stateGroupFactory) {
        return new SimpleStateGroupPool(stateGroupFactory);
    }


    @Bean
    StateGroupSchedulerFactory stateGroupSchedulerFactory(StateGroupSchedulerFactoryConfig stateGroupSchedulerFactoryConfig) {
        return new SimpleStateGroupSchedulerFactory(stateGroupSchedulerFactoryConfig);
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
        return new GameStateGroupSchedulerManager(stateGroupPool, stateGroupSchedulerFactory, eventPublishSchedulerFactory, schedulerManagerConfig);
    }
}
