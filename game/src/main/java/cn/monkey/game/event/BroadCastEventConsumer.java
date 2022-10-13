package cn.monkey.game.event;

import com.lmax.disruptor.EventHandler;

public class BroadCastEventConsumer implements EventHandler<ServerBroadCastEvent> {
    @Override
    public void onEvent(ServerBroadCastEvent event, long sequence, boolean endOfBatch) throws Exception {
        // TODO
        //
    }
}
