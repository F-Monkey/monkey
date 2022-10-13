package cn.monkey.state.scheduler;

import cn.monkey.state.scheduler.strategy.WaitingStrategy;
import com.google.common.base.Preconditions;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class SimpleEventPublishSchedulerFactory implements EventPublishSchedulerFactory {

    protected ThreadFactory threadFactory = Executors.defaultThreadFactory();

    protected WaitingStrategy waitingStrategy = WaitingStrategy.blocking();

    @Override
    public EventPublishScheduler create(long id) {
        return new SimpleEventPublishScheduler(id, this.waitingStrategy, this.threadFactory);
    }

    @Override
    public void setThreadFactory(ThreadFactory threadFactory) {
        Preconditions.checkNotNull(threadFactory);
        this.threadFactory = threadFactory;
    }

    @Override
    public void setWaitingStrategy(WaitingStrategy waitingStrategy) {
        Preconditions.checkNotNull(waitingStrategy);
        this.waitingStrategy = waitingStrategy;
    }
}
