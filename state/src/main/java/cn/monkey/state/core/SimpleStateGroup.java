package cn.monkey.state.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SimpleStateGroup extends AbstractStateGroup {

    public SimpleStateGroup(String id, StateContext stateContext, boolean canAutoUpdate) {
        super(id, stateContext, canAutoUpdate);
    }

    @Override
    protected BlockingQueue<Object> createEventQueue() {
        return new ArrayBlockingQueue<>(1024);
    }

    @Override
    protected Map<String, State> createStateMap() {
        return new HashMap<>();
    }
}
