package cn.monkey.game.event;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

public class HallServerBroadCastHandler implements EventHandler<Object> {

    private final Disruptor<ServerBroadCastEvent> disruptor;

    public HallServerBroadCastHandler(int bufferSize,
                                      int consumerSize) {
        this.disruptor = new Disruptor<>(new ServerBroadCastEventFactory(), bufferSize, Executors.defaultThreadFactory());
        BroadCastEventConsumer[] eventHandlers = new BroadCastEventConsumer[consumerSize];
        for (int i = 0; i < consumerSize; i++) {
            eventHandlers[i] = new BroadCastEventConsumer();
        }
        this.disruptor.handleEventsWith(eventHandlers);
        this.disruptor.start();
    }

    @Override
    public void accept(Object e) {
        RingBuffer<ServerBroadCastEvent> ringBuffer = this.disruptor.getRingBuffer();
        long next = ringBuffer.next();
        try {
            ServerBroadCastEvent event = ringBuffer.get(next);
            event.setEvent(e);
        } finally {
            ringBuffer.publish(next);
        }
    }
}
