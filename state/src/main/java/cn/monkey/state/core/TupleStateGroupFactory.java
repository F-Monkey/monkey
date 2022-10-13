package cn.monkey.state.core;

import cn.monkey.commons.utils.Timer;

public abstract class TupleStateGroupFactory<T> implements StateGroupFactory {

    protected final Timer timer;

    public TupleStateGroupFactory(Timer timer) {
        this.timer = timer;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final StateGroup create(String id, Object... args) {
        return this.create(id, (T) args[0]);
    }

    public abstract StateGroup create(String id, T t);
}
