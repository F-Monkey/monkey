package cn.monkey.server.netty.session;

import cn.monkey.commons.utils.Timer;
import io.netty.channel.ChannelHandlerContext;

public class HeartBeatNettySession extends SimpleNettySession {

    protected final Timer timer;

    private volatile long lastOperateTime;

    private long timeThreshold = 5000;

    public HeartBeatNettySession(ChannelHandlerContext ctx,
                                 Timer timer) {
        super(ctx);
        this.timer = timer;
        this.refreshLastOperate();
    }

    public void refreshLastOperate() {
        this.lastOperateTime = timer.getCurrentTimeMs();
    }

    public void setTimeThreshold(long timeThreshold) {
        this.timeThreshold = timeThreshold;
    }

    @Override
    public boolean isActive() {
        return super.isActive() && (this.timer.getCurrentTimeMs() - lastOperateTime > timeThreshold);
    }
}
