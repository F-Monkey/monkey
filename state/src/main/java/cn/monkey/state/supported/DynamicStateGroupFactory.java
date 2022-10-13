package cn.monkey.state.supported;

import cn.monkey.commons.utils.Timer;
import cn.monkey.state.core.*;
import cn.monkey.state.core.SimpleStateGroup;
import cn.monkey.state.core.StateContext;
import cn.monkey.state.core.StateGroup;
import cn.monkey.state.core.TupleStateGroupFactory;
import cn.monkey.state.supported.data.StateConfig;
import cn.monkey.state.supported.data.StateGroupConfig;
import cn.monkey.state.supported.data.StateGroupConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class DynamicStateGroupFactory extends TupleStateGroupFactory<String> {

    private final Logger log = LoggerFactory.getLogger(DynamicStateGroupFactory.class);
    protected final StateGroupConfigRepository stateGroupConfigRepository;

    protected final DynamicStateFactory stateFactory;

    public DynamicStateGroupFactory(Timer timer,
                                    StateGroupConfigRepository stateGroupConfigRepository,
                                    DynamicStateFactory stateFactory) {
        super(timer);
        this.stateGroupConfigRepository = stateGroupConfigRepository;
        this.stateFactory = stateFactory;
    }

    @Override
    public StateGroup create(String id, String configId) {
        StateGroupConfig stateGroupConfig = this.stateGroupConfigRepository.find(configId);
        if (null == stateGroupConfig) {
            log.error("can not find stateGroupConfig by id: {}", configId);
            throw new IllegalArgumentException("invalid stateGroupConfig Id: " + configId);
        }
        List<StateConfig> stateConfigList = stateGroupConfig.getStateConfigList();
        if (CollectionUtils.isEmpty(stateConfigList)) {
            log.error("bad stateGroupConfig id: {}, cause state config is empty", configId);
            throw new IllegalArgumentException("stateGroupConfig: " + id + " stateConfigList is empty");
        }

        StateGroup stateGroup = new SimpleStateGroup(id, StateContext.EMPTY, stateGroupConfig.isCanAutoUpdate());
        for (StateConfig sc : stateConfigList) {
            DynamicState dynamicState = this.stateFactory.create(stateGroup, sc.getCode(), sc);
            stateGroup.addState(dynamicState);
        }
        stateGroup.setStartState(stateGroupConfig.getStartState());
        return stateGroup;
    }
}
