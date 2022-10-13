package cn.monkey.server;

public interface SessionFactory<T> {
    Session create(T t);
}
