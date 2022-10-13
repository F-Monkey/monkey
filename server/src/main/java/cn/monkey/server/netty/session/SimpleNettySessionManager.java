package cn.monkey.server.netty.session;

import cn.monkey.commons.bean.Refreshable;
import cn.monkey.server.Session;
import cn.monkey.server.SessionFactory;
import cn.monkey.server.SessionManager;
import cn.monkey.server.SessionUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleNettySessionManager implements SessionManager<ChannelHandlerContext>, Refreshable {

    protected final SessionFactory<ChannelHandlerContext> sessionFactory;
    private volatile ConcurrentHashMap<String, Session> sessionMap;

    public SimpleNettySessionManager(SessionFactory<ChannelHandlerContext> sessionFactory) {
        this.sessionMap = new ConcurrentHashMap<>();
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Session findOrCreate(ChannelHandlerContext ctx) {
        final ConcurrentHashMap<String, Session> sessionMap = this.sessionMap;
        Session session = sessionMap.compute(SessionUtil.getId(ctx), (k, v) -> {
            if (null == v) {
                return this.sessionFactory.create(ctx);
            }
            if (!v.isActive()) { // NEVER HAPPENED
                return this.sessionFactory.create(ctx);
            }
            return v;
        });
        this.sessionMap = sessionMap;
        return session;
    }

    @Override
    public void refresh() {
        Iterator<Map.Entry<String, Session>> iterator = this.sessionMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Session> next = iterator.next();
            Session value = next.getValue();
            if (!value.isActive()) {
                iterator.remove();
            }
        }
    }
}
