package cn.monkeyframework.commons.data.cache;

public interface LocalCache<T> {
    T get();

    void set(T t);
}
