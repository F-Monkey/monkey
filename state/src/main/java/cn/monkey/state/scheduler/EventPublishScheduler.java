package cn.monkey.state.scheduler;

public interface EventPublishScheduler extends Scheduler {
    void publish(Runnable event);
}
