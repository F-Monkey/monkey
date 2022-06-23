package cn.monkeyframework.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class AutoTask {

    private static final Logger log = LoggerFactory.getLogger(AutoTask.class);

    protected final ScheduledExecutorService executorService;

    public AutoTask(int taskSize) {
        this.executorService = new ScheduledThreadPoolExecutor(taskSize, Executors.defaultThreadFactory(), (r, executor) -> log.error("task rejected: {}", r));
    }

    public void addTask(Runnable task, int period) {
        this.executorService.scheduleAtFixedRate(task, 0, period, TimeUnit.MILLISECONDS);
    }
}
