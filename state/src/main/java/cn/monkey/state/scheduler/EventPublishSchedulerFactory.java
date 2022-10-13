package cn.monkey.state.scheduler;

import cn.monkey.state.scheduler.strategy.WaitingStrategy;

import java.util.concurrent.ThreadFactory;

public interface EventPublishSchedulerFactory extends SchedulerFactory {
    EventPublishScheduler create(long id);


    void setWaitingStrategy(WaitingStrategy waitingStrategy);
}
