package cn.monkey.game.state.core;

import cn.monkey.commons.utils.Timer;
import cn.monkey.state.core.SimpleStateGroup;

public class GameStateGroup extends SimpleStateGroup {
    public GameStateGroup(String id, GameStateContext stateContext, Timer timer, boolean canAutoUpdate) {
        super(id, stateContext, timer, canAutoUpdate);
    }

    @Override
    public GameStateContext getStateContext() {
        return (GameStateContext) super.getStateContext();
    }

    @Override
    public void flush() {
        // todo save game StateContext data
    }
}
