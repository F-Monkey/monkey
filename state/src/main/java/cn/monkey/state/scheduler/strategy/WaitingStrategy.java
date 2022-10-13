package cn.monkey.state.scheduler.strategy;

public interface WaitingStrategy {
    default void await() throws InterruptedException {
    }

    default void signalAllWhenBlocking() {

    }

    static WaitingStrategy blocking() {
        return new BlockingWaitingStrategy();
    }

    static WaitingStrategy sleeping(long waitTime) {
        return new SleepingWaitingStrategy(waitTime);
    }
}
