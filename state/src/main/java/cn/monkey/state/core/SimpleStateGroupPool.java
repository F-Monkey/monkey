package cn.monkey.state.core;

import cn.monkey.commons.bean.Refreshable;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleStateGroupPool implements StateGroupPool, Refreshable {

    protected final StateGroupFactory stateGroupFactory;
    protected volatile ConcurrentHashMap<String, StateGroup> stateGroupMap;

    public SimpleStateGroupPool(StateGroupFactory stateGroupFactory) {
        this.stateGroupMap = new ConcurrentHashMap<>();
        this.stateGroupFactory = stateGroupFactory;
    }

    @Override
    public FetchStateGroup findOrCreate(String id, Object... args) {
        boolean[] isNew = {false};
        final ConcurrentHashMap<String, StateGroup> stateGroupMap = this.stateGroupMap;
        StateGroup stateGroup = stateGroupMap.computeIfAbsent(id, (key) -> {
            isNew[0] = true;
            return this.stateGroupFactory.create(key, args);
        });
        this.stateGroupMap = stateGroupMap;
        return new FetchStateGroup(isNew[0], stateGroup);
    }

    @Override
    public void refresh() {
        final ConcurrentHashMap<String, StateGroup> stateGroupMap = this.stateGroupMap;
        Iterator<Map.Entry<String, StateGroup>> iterator = stateGroupMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, StateGroup> next = iterator.next();
            StateGroup value = next.getValue();
            if (value.canClose()) {
                value.close();
            }
            iterator.remove();
        }
    }
}
