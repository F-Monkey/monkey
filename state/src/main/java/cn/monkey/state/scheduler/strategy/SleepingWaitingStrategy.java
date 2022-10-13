package cn.monkey.state.scheduler.strategy;

import com.google.common.base.Preconditions;

import java.util.concurrent.locks.LockSupport;

class SleepingWaitingStrategy implements WaitingStrategy {

    private final long waitTime;

    SleepingWaitingStrategy(long waitTime) {
        Preconditions.checkArgument(waitTime > 0,"[waitTime] must be more than 0");
        this.waitTime = waitTime;
    }

    @Override
    public void await() {
        LockSupport.parkNanos(this.waitTime);
    }
}
