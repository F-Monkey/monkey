package cn.monkey.state.scheduler;

import cn.monkey.state.scheduler.strategy.WaitingStrategy;

public interface StateGroupSchedulerFactory extends SchedulerFactory {
    StateGroupScheduler create(long id);

    void setWaitingStrategy(WaitingStrategy waitingStrategy);

}
