package cn.monkey.state.scheduler;

import cn.monkey.commons.bean.Countable;
import cn.monkey.commons.bean.Refreshable;
import cn.monkey.state.core.StateGroup;
import cn.monkey.state.core.StateGroupPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleSchedulerManager implements SchedulerManager, Countable, Refreshable {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final StateGroupPool stateGroupPool;

    protected final StateGroupSchedulerFactory stateGroupSchedulerFactory;

    protected final EventPublishSchedulerFactory eventPublishSchedulerFactory;

    protected final EventPublishScheduler[] eventPublishSchedulers;

    protected final SchedulerManagerConfig schedulerManagerConfig;

    protected volatile ConcurrentHashMap<Long, StateGroupScheduler> stateGroupSchedulerMap;

    private final AtomicLong stateGroupSchedulerIdCounter = new AtomicLong(0);

    public SimpleSchedulerManager(StateGroupPool stateGroupPool,
                                  StateGroupSchedulerFactory stateGroupSchedulerFactory,
                                  EventPublishSchedulerFactory eventPublishSchedulerFactory,
                                  SchedulerManagerConfig managerConfig) {
        this.stateGroupPool = stateGroupPool;
        this.stateGroupSchedulerFactory = stateGroupSchedulerFactory;
        this.eventPublishSchedulerFactory = eventPublishSchedulerFactory;
        this.schedulerManagerConfig = managerConfig;
        this.eventPublishSchedulers = this.initEventPublishSchedulers();
        this.stateGroupSchedulerMap = this.createStateGroupSchedulerMap();
    }

    protected ConcurrentHashMap<Long, StateGroupScheduler> createStateGroupSchedulerMap() {
        return new ConcurrentHashMap<>();
    }

    protected final EventPublishScheduler[] initEventPublishSchedulers() {
        int eventPublisherSchedulerSize = this.schedulerManagerConfig.getEventPublisherSchedulerSize();
        if (eventPublisherSchedulerSize != 1 && Integer.bitCount(eventPublisherSchedulerSize) != 1) {
            throw new IllegalArgumentException("bufferSize must be a power of 2 or equals to 1");
        }
        EventPublishScheduler[] eventPublishSchedulers = new EventPublishScheduler[eventPublisherSchedulerSize];
        for (int i = 0; i < eventPublisherSchedulerSize; i++) {
            eventPublishSchedulers[i] = this.eventPublishSchedulerFactory.create(i);
            eventPublishSchedulers[i].start();
        }
        return eventPublishSchedulers;
    }

    protected final EventPublishScheduler findEventPublisherScheduler(String groupId) {
        int length = this.eventPublishSchedulers.length;
        return this.eventPublishSchedulers[groupId.hashCode() & (length - 1)];
    }

    protected void findBestGroupScheduler2AddStateGroup(String groupId, Object event, Object... args) {
        StateGroupPool.FetchStateGroup fetchStateGroup = this.stateGroupPool.findOrCreate(groupId, args);
        StateGroup stateGroup = fetchStateGroup.getStateGroup();
        if (!fetchStateGroup.isNew()) {
            stateGroup.addEvent(event);
            return;
        }
        final ConcurrentHashMap<Long, StateGroupScheduler> stateGroupSchedulerMap = this.stateGroupSchedulerMap;
        for (StateGroupScheduler scheduler : stateGroupSchedulerMap.values()) {
            if (scheduler.isFull()) {
                continue;
            }
            if (!scheduler.isStarted()) {
                continue;
            }
            if (scheduler.tryAddStateGroup(stateGroup)) {
                stateGroup.addEvent(event);
                return;
            }
        }
        if (this.isFull()) {
            throw new IllegalArgumentException("can not add any stateGroupScheduler");
        }
        StateGroupScheduler scheduler = this.stateGroupSchedulerFactory.create(stateGroupSchedulerIdCounter.getAndIncrement());
        scheduler.start();
        scheduler.tryAddStateGroup(stateGroup);
        stateGroup.addEvent(event);
        stateGroupSchedulerMap.put(scheduler.id(), scheduler);
        this.stateGroupSchedulerMap = stateGroupSchedulerMap;
    }


    @Override
    public void addEvent(String groupId, Object event) {
        this.findEventPublisherScheduler(groupId).publish(() -> this.findBestGroupScheduler2AddStateGroup(groupId, event));
    }

    @Override
    public boolean isEmpty() {
        return this.stateGroupSchedulerMap.isEmpty();
    }

    @Override
    public synchronized int size() {
        return this.stateGroupSchedulerMap.size();
    }

    @Override
    public boolean isFull() {
        return this.size() >= this.schedulerManagerConfig.getStateGroupSchedulerSize();
    }

    @Override
    public void refresh() {
        final ConcurrentHashMap<Long, StateGroupScheduler> stateGroupSchedulerMap = new ConcurrentHashMap<>();
        int size = this.stateGroupSchedulerMap.size();
        if (size == 0) {
            return;
        }
        for (StateGroupScheduler scheduler : this.stateGroupSchedulerMap.values()) {
            if (scheduler.isEmpty() && size > this.schedulerManagerConfig.getStateGroupSchedulerCoreSize()) {
                scheduler.stop();
                size--;
                continue;
            }
            stateGroupSchedulerMap.put(scheduler.id(), scheduler);
        }
        this.stateGroupSchedulerMap = stateGroupSchedulerMap;
    }
}
