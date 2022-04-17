package cn.monkeyframework.common.spring.context;

import cn.monkeyframework.common.util.ClassUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public abstract class AbstractBeanContainer<T> implements ApplicationContextAware, InitializingBean, BeanContainer<T> {

    protected final Class<T> type;

    protected ApplicationContext applicationContext;

    protected Map<String, T> beanMap;

    @SuppressWarnings("unchecked")
    public AbstractBeanContainer() {
        Class<?> clazz = ClassUtil.getActualType(this.getClass());
        this.type = (Class<T>) clazz;
        this.beanMap = Collections.emptyMap();
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, T> beansOfType = this.applicationContext.getBeansOfType(this.type);
        this.beanMap = ImmutableMap.copyOf(beansOfType);
    }

    @Override
    public T getBean(String name) {
        return this.beanMap.get(name);
    }

    @Override
    public Collection<T> getAll() {
        return ImmutableList.copyOf(this.beanMap.values());
    }
}
