package cn.monkeyframework.commons.spring.context;

import java.util.function.Predicate;

public interface Remover<V> {
    void remove(Predicate<V> predicate);
}
