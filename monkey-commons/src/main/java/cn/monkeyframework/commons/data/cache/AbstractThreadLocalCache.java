package cn.monkeyframework.commons.data.cache;

import io.netty.util.concurrent.FastThreadLocal;

public abstract class AbstractThreadLocalCache<T> implements LocalCache<T> {
    private final FastThreadLocal<T> threadLocal;

    public AbstractThreadLocalCache() {
        this.threadLocal = new FastThreadLocal<>();
    }

    public void set(T t) {
        this.threadLocal.set(t);
    }

    public T get() {
        return this.threadLocal.get();
    }

}
