package cn.monkey.hall.state;

import cn.monkey.commons.utils.Timer;
import cn.monkey.commons.data.repository.ServerRepository;
import cn.monkey.state.core.SimpleStateGroup;
import cn.monkey.state.core.SimpleStateGroupFactory;
import cn.monkey.state.core.StateGroup;

public class HallServerStateGroupFactory extends SimpleStateGroupFactory {

    private final ServerRepository roomServerRepository;

    public HallServerStateGroupFactory(Timer timer,
                                       ServerRepository roomServerRepository) {
        super(timer);
        this.roomServerRepository = roomServerRepository;
    }

    @Override
    public StateGroup create(String id, Object... args) {
        HallServerContext hallServerContext = new HallServerContext(this.roomServerRepository);
        SimpleStateGroup stateGroup = new SimpleStateGroup(id, hallServerContext, true);
        ServerState serverState = new ServerState(super.timer, stateGroup);
        stateGroup.addState(serverState);
        stateGroup.setStartState(ServerState.CODE);
        return stateGroup;
    }
}
