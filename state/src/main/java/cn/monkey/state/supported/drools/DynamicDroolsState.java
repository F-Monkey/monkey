package cn.monkey.state.supported.drools;

import cn.monkey.commons.utils.Timer;
import cn.monkey.state.core.StateGroup;
import cn.monkey.state.supported.DynamicState;

public class DynamicDroolsState extends DynamicState {
    public DynamicDroolsState(String code, Timer timer, StateGroup stateGroup) {
        super(code, timer, stateGroup);
    }
}
