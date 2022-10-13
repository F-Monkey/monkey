package cn.monkey.state.core;

import cn.monkey.commons.utils.Timer;

public class EmptyState extends AbstractState {

    public static final String CODE = "empty";

    public EmptyState(Timer timer, StateGroup stateGroup) {
        super(CODE, timer, stateGroup);
    }

    @Override
    public String finish() throws Exception {
        return null;
    }
}
