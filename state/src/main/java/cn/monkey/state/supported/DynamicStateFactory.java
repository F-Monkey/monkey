package cn.monkey.state.supported;

import cn.monkey.state.core.StateGroup;
import cn.monkey.state.supported.data.StateConfig;

public interface DynamicStateFactory {
    DynamicState create(StateGroup stateGroup, String code, StateConfig stateConfig);
}
