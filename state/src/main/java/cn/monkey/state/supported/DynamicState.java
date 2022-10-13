package cn.monkey.state.supported;

import cn.monkey.commons.utils.Timer;
import cn.monkey.state.core.AbstractState;
import cn.monkey.state.core.State;
import cn.monkey.state.core.StateGroup;
import cn.monkey.state.supported.data.StateConfig;

public class DynamicState extends AbstractState implements State {

    public DynamicState(String code, Timer timer, StateGroup stateGroup) {
        super(code, timer, stateGroup);
    }


    @Override
    public String finish() throws Exception {
        return null;
    }
}
