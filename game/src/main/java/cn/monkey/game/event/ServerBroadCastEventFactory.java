package cn.monkey.game.event;

import com.lmax.disruptor.EventFactory;

public class ServerBroadCastEventFactory implements EventFactory<ServerBroadCastEvent> {
    @Override
    public ServerBroadCastEvent newInstance() {
        return new ServerBroadCastEvent();
    }
}
