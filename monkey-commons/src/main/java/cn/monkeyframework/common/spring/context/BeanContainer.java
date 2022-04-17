package cn.monkeyframework.common.spring.context;

import java.util.Collection;

public interface BeanContainer<T> {
    T getBean(String name);

    Collection<T> getAll();
}
