package cn.monkey.hall.server;

import cn.monkey.proto.Command;
import cn.monkey.server.Dispatcher;
import cn.monkey.server.Filter;
import cn.monkey.server.netty.session.SimpleNettySessionManager;
import cn.monkey.server.netty.tcp.NettyWebSocketServer;
import cn.monkey.server.netty.tcp.ProtoBufWebSocketChannelHandler;
import io.netty.channel.ChannelHandler;
import io.netty.util.internal.TypeParameterMatcher;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HallServerApplicationRunner implements ApplicationContextAware, ApplicationRunner {

    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.runServer();
    }

    @SuppressWarnings("unchecked")
    private void runServer() throws InterruptedException {
        Map<String, Filter> beansOfType = applicationContext.getBeansOfType(Filter.class);
        List<Filter<Command.Package>> filters = new ArrayList<>();
        Command.Package pkg = Command.Package.newBuilder().build();
        for (Filter<?> filter : beansOfType.values()) {
            TypeParameterMatcher matcher = TypeParameterMatcher.find(filter, Filter.class, "Pkg");
            if (matcher.match(pkg)) {
                filters.add((Filter<Command.Package>) filter);
            }
        }
        Dispatcher dispatcher = this.applicationContext.getBean(Dispatcher.class);
        TypeParameterMatcher matcher = TypeParameterMatcher.find(dispatcher, Dispatcher.class, "Pkg");
        if (!matcher.match(pkg)) {
            dispatcher = null;
        }
        SimpleNettySessionManager sessionManager = applicationContext.getBean(SimpleNettySessionManager.class);

        ChannelHandler channelHandler = new ProtoBufWebSocketChannelHandler(sessionManager, filters, dispatcher);
        NettyWebSocketServer server = new NettyWebSocketServer(channelHandler, "", 8888, 4, 5);
        server.start();
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
