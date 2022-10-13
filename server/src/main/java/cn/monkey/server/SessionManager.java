package cn.monkey.server;

public interface SessionManager<T> {
    Session findOrCreate(T ctx);
}
