package cn.monkey.state.core;

import cn.monkey.commons.utils.Timer;

public class SimpleStateGroupFactory implements StateGroupFactory {

    protected boolean canAutoUpdate = false;

    protected final Timer timer;

    public SimpleStateGroupFactory(Timer timer) {
        this.timer = timer;
    }


    @Override
    public StateGroup create(String id, Object... args) {
        StateGroup stateGroup = new SimpleStateGroup(id, StateContext.EMPTY, this.canAutoUpdate);
        State state = new EmptyState(this.timer, stateGroup);
        stateGroup.addState(state);
        stateGroup.setStartState(EmptyState.CODE);
        return stateGroup;
    }
}
