package cn.monkey.commons.bean;

import java.util.Collection;

public interface BeanContext<T> {
    T getBean(String name);

    Collection<T> getBeans();

    boolean containsBean(String name);
}
