package cn.monkey.state.scheduler;

import cn.monkey.state.scheduler.strategy.WaitingStrategy;
import com.google.common.base.Preconditions;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class SimpleStateGroupSchedulerFactory implements StateGroupSchedulerFactory {

    protected final StateGroupSchedulerFactoryConfig stateGroupFactoryConfig;

    protected ThreadFactory threadFactory = Executors.defaultThreadFactory();

    protected WaitingStrategy waitingStrategy;

    public SimpleStateGroupSchedulerFactory(StateGroupSchedulerFactoryConfig stateGroupFactoryConfig) {
        this.stateGroupFactoryConfig = stateGroupFactoryConfig;
        this.waitingStrategy = WaitingStrategy.sleeping(stateGroupFactoryConfig.getUpdateFrequency());
    }

    @Override
    public StateGroupScheduler create(long id) {
        int maxSize = this.stateGroupFactoryConfig.getMaxSize();
        if (1 == maxSize) {
            return new SingleStateGroupScheduler(id, this.waitingStrategy, this.threadFactory);
        }
        return new SimpleStateGroupScheduler(id,
                this.waitingStrategy,
                this.threadFactory,
                maxSize);
    }

    @Override
    public void setThreadFactory(ThreadFactory threadFactory) {
        Preconditions.checkNotNull(threadFactory);
        this.threadFactory = threadFactory;
    }

    @Override
    public void setWaitingStrategy(WaitingStrategy waitingStrategy) {
        this.waitingStrategy = waitingStrategy;
    }
}
