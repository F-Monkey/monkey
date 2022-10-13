package cn.monkey.state.supported.data;

import cn.monkey.state.core.StateContext;

import java.util.List;

public class StateGroupConfig implements StateContext {

    private boolean canAutoUpdate;

    private String startState;

    private String id;

    private List<StateConfig> stateConfigList;

    public void setStateConfigList(List<StateConfig> stateConfigList) {
        this.stateConfigList = stateConfigList;
    }

    public List<StateConfig> getStateConfigList() {
        return stateConfigList;
    }

    public boolean isCanAutoUpdate() {
        return canAutoUpdate;
    }

    public void setCanAutoUpdate(boolean canAutoUpdate) {
        this.canAutoUpdate = canAutoUpdate;
    }

    public String getStartState() {
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
