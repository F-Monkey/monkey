package cn.monkey.game.state.core;

import cn.monkey.state.core.SimpleStateGroup;

public class GameStateGroup extends SimpleStateGroup {
    public GameStateGroup(String id, GameStateContext stateContext, boolean canAutoUpdate) {
        super(id, stateContext, canAutoUpdate);
    }

    @Override
    public GameStateContext getStateContext() {
        return (GameStateContext)super.getStateContext();
    }
}
