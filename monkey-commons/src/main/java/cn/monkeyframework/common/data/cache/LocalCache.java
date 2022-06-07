package cn.monkeyframework.common.data.cache;

public interface LocalCache<T> {
    T get();

    void set(T t);
}
