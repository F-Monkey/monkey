package cn.monkeyframework.commons.data.jpa;

import cn.monkeyframework.commons.data.repository.jpa.JpaRepositoryFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = JpaRepositoryFactoryBean.class)
public class JpaAutoConfig {

    private static final Logger log = LoggerFactory.getLogger(JpaAutoConfig.class);

    public JpaAutoConfig() {
        log.debug("jpa autoConfig init");
    }
}
