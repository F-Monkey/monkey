package cn.monkey.state.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public abstract class AbstractStateGroup implements StateGroup {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String id;

    private final StateContext stateContext;

    protected final Map<String, State> stateMap;

    protected final BlockingQueue<Object> eventQueue;

    protected final boolean canAutoUpdate;

    protected State currentState;

    public AbstractStateGroup(String id,
                              StateContext stateContext,
                              boolean canAutoUpdate) {
        this.id = id;
        this.stateContext = stateContext;
        this.canAutoUpdate = canAutoUpdate;
        this.stateMap = this.createStateMap();
        this.eventQueue = this.createEventQueue();
    }

    protected abstract BlockingQueue<Object> createEventQueue();

    protected abstract Map<String, State> createStateMap();

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public void addState(State state) {
        this.stateMap.put(state.code(), state);
    }

    @Override
    public void setStartState(String stateCode) {
        this.currentState = Objects.requireNonNull(this.stateMap.get(stateCode));
    }

    @Override
    public StateContext getStateContext() {
        return this.stateContext;
    }

    @Override
    public void addEvent(Object event) {
        if (!this.eventQueue.offer(event)) {
            log.error("group: {} eventQueue is full", this.id());
        }
    }

    @Override
    public void update() {
        if (null == this.currentState) {
            return;
        }
        try {
            this.currentState.init();
        } catch (Exception e) {
            this.currentState.initOnError(e);
        }
        Object event = this.eventQueue.poll();
        if (null == event) {
            if (!this.canAutoUpdate) {
                return;
            }
            this.updateAndTrySwitch2NextState();
            return;
        }
        try {
            this.currentState.fireEvent(event);
        } catch (Exception e) {
            this.currentState.fireEventOnError(event, e);
        }
        this.updateAndTrySwitch2NextState();
    }

    protected void updateAndTrySwitch2NextState() {
        StateInfo stateInfo = new StateInfo();
        try {
            this.currentState.update(stateInfo);
        } catch (Exception e) {
            this.currentState.updateOnError(stateInfo, e);
        }
        if (!stateInfo.isFinish) {
            return;
        }
        String nextStateCode;
        try {
            nextStateCode = this.currentState.finish();
        } catch (Exception e) {
            nextStateCode = this.currentState.finishOnError(e);
        }
        if (null == nextStateCode) {
            this.currentState = null;
            return;
        }
        this.currentState = this.stateMap.get(nextStateCode);
    }

    @Override
    public boolean canClose() {
        return this.currentState == null;
    }

    @Override
    public void close() {

    }

    @Override
    public void flush() {

    }
}
