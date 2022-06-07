package cn.monkeyframework.common.data.repository.jpa;

import cn.monkeyframework.common.data.cache.user.UserLocalCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.lang.Nullable;

import javax.persistence.EntityManager;

public class JpaRepositoryFactoryBean<T extends Repository<S, ID>, S, ID>
        extends org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean<T, S, ID>
        implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(JpaRepositoryFactoryBean.class);
    protected EntityPathResolver entityPathResolver;
    protected EscapeCharacter escapeCharacter = EscapeCharacter.DEFAULT;
    protected JpaQueryMethodFactory queryMethodFactory;
    protected ApplicationContext applicationContext;
    protected UserLocalCache userLocalCache;

    /**
     * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public JpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    public void setEscapeCharacter(char escapeCharacter) {
        this.escapeCharacter = EscapeCharacter.of(escapeCharacter);
    }

    @Override
    @Autowired
    public void setEntityPathResolver(ObjectProvider<EntityPathResolver> resolver) {
        this.entityPathResolver = resolver.getIfAvailable(() -> SimpleEntityPathResolver.INSTANCE);
        super.setEntityPathResolver(resolver);
    }

    @Autowired
    @Override
    public void setQueryMethodFactory(@Nullable JpaQueryMethodFactory factory) {
        if (factory != null) {
            this.queryMethodFactory = factory;
        }
        super.setQueryMethodFactory(factory);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        JpaRepositoryFactory jpaRepositoryFactory = new JpaRepositoryFactory(entityManager);
        jpaRepositoryFactory.setEntityPathResolver(entityPathResolver);
        jpaRepositoryFactory.setEscapeCharacter(escapeCharacter);
        jpaRepositoryFactory.setUserLocalCache(userLocalCache);
        if (queryMethodFactory != null) {
            jpaRepositoryFactory.setQueryMethodFactory(queryMethodFactory);
        }

        return jpaRepositoryFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        try {
            this.userLocalCache = this.applicationContext.getBean(UserLocalCache.class);
        } catch (BeansException e) {
            log.warn("can not find userLocalCache...");
        }
    }
}
