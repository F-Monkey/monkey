package cn.monkey.server.netty.tcp;

import cn.monkey.server.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyWebSocketServer implements Server {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final ServerBootstrap bootstrap;

    protected final EventLoopGroup bossGroup;

    protected final EventLoopGroup workerGroup;

    protected final ChannelHandler customerHandler;

    protected final String protocol;

    protected final int port;

    public NettyWebSocketServer(ChannelHandler customerHandler,
                                String protocol,
                                int port,
                                int bossSize,
                                int workerSize) {
        this.bootstrap = new ServerBootstrap();
        this.bossGroup = new NioEventLoopGroup(bossSize);
        this.workerGroup = new NioEventLoopGroup(workerSize);
        this.customerHandler = customerHandler;
        this.protocol = protocol;
        this.port = port;
        // ws://ip:port/[protocol]
    }

    @Override
    public void start() {
        this.bootstrap.group(this.bossGroup, this.workerGroup)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.SO_BACKLOG, 1024)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new HttpObjectAggregator(65536));
                        pipeline.addLast(new WebSocketServerProtocolHandler(protocol, null,
                                true, 65336 * 10));
                        pipeline.addLast(customerHandler);
                    }
                });

        try {
            ChannelFuture channelFuture = this.bootstrap.bind(this.port).sync();
            log.info("server start at port: {}", this.port);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.bossGroup.shutdownGracefully();
            this.workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }
}
