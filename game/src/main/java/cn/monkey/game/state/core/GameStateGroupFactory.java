package cn.monkey.game.state.core;

import cn.monkey.commons.utils.Timer;
import cn.monkey.game.data.GameConfig;
import cn.monkey.game.event.HallServerBroadCastHandler;
import cn.monkey.game.repository.GameConfigRepository;
import cn.monkey.state.core.SimpleStateGroupFactory;
import cn.monkey.state.core.StateGroup;
import cn.monkey.state.core.TupleStateGroupFactory;

public class GameStateGroupFactory extends TupleStateGroupFactory<String> {

    protected final GameConfigRepository repository;

    protected final HallServerBroadCastHandler serverBroadCastHandler;

    public GameStateGroupFactory(Timer timer,
                                 GameConfigRepository repository,
                                 HallServerBroadCastHandler serverBroadCastHandler) {
        super(timer);
        this.repository = repository;
        this.serverBroadCastHandler = serverBroadCastHandler;
    }

    @Override
    public StateGroup create(String id, String configId) {
        GameConfig gameConfig = this.repository.findById(configId);
        GameStateContext gameStateContext = new GameStateContext(gameConfig, serverBroadCastHandler);
        GameStateGroup gameStateGroup = new GameStateGroup(id, gameStateContext, false);
        gameStateGroup.addState(new StartState(super.timer, gameStateGroup));
        gameStateGroup.addState(new WaitingState(super.timer, gameStateGroup));
        gameStateGroup.setStartState(WaitingState.CODE);
        return gameStateGroup;
    }
}
