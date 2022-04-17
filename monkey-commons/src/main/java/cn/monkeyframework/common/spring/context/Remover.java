package cn.monkeyframework.common.spring.context;

import java.util.function.Predicate;

public interface Remover<V> {
    void remove(Predicate<V> predicate);
}
