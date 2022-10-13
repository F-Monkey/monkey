package cn.monkey.game.bean;

import cn.monkey.commons.bean.Refresh;
import cn.monkey.commons.bean.spring.AbstractBeanContext;

import java.util.Map;

public class RefreshBeanContext extends AbstractBeanContext<Object> {

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(Refresh.class);
    }
}
