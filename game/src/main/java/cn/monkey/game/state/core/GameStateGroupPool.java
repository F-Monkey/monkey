package cn.monkey.game.state.core;

import cn.monkey.commons.bean.Refresh;
import cn.monkey.commons.bean.Refreshes;
import cn.monkey.state.core.StateGroup;
import cn.monkey.state.core.StateGroupFactory;
import cn.monkey.state.core.StateGroupPool;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Refreshes({
        @Refresh(method = "flushStateGroup"),
        @Refresh(method = "refreshCloseableGroup")
})
public class GameStateGroupPool implements StateGroupPool {

    protected final StateGroupFactory stateGroupFactory;
    protected volatile ConcurrentHashMap<String, StateGroup> stateGroupMap;

    public GameStateGroupPool(StateGroupFactory stateGroupFactory) {
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

    public void flushStateGroup() {
        for (StateGroup stateGroup : stateGroupMap.values()) {
            stateGroup.flush();
        }
    }

    public void refreshCloseableGroup() {
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
