package cn.monkey.server.netty.session;

import cn.monkey.server.Session;
import cn.monkey.server.SessionFactory;
import io.netty.channel.ChannelHandlerContext;

public class SimpleNettySessionFactory implements SessionFactory<ChannelHandlerContext> {
    @Override
    public Session create(ChannelHandlerContext channelHandlerContext) {
        return new SimpleNettySession(channelHandlerContext);
    }
}
