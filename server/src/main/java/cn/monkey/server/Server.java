package cn.monkey.server;

public interface Server {
    void start() throws InterruptedException;

    void stop();
}
