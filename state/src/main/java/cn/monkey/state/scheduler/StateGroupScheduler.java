package cn.monkey.state.scheduler;

import cn.monkey.commons.bean.Countable;
import cn.monkey.state.core.StateGroup;

public interface StateGroupScheduler extends Scheduler, Countable {
    boolean tryAddStateGroup(StateGroup stateGroup);
}
