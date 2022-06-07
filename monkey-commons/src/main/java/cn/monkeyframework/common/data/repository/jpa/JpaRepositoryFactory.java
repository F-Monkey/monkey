package cn.monkeyframework.common.data.repository.jpa;

import cn.monkeyframework.common.data.cache.user.UserLocalCache;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class JpaRepositoryFactory extends org.springframework.data.jpa.repository.support.JpaRepositoryFactory {
    /**
     * Creates a new {@link JpaRepositoryFactory}.
     *
     * @param entityManager must not be {@literal null}
     */

    private UserLocalCache userLocalCache;

    public JpaRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
    }

    public void setUserLocalCache(@Nullable UserLocalCache userLocalCache) {
        this.userLocalCache = userLocalCache;
    }

    @Override
    protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
        if (JpaRepository.class.isAssignableFrom(information.getRepositoryBaseClass())) {
            JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
            Object repository = getTargetRepositoryViaReflection(information, entityInformation, entityManager);
            Assert.isInstanceOf(JpaRepository.class, repository);
            JpaRepository<?, ?> jpaRepository = (JpaRepository<?, ?>) repository;
            if (jpaRepository instanceof DefaultJpaRepository) {
                ((DefaultJpaRepository<?, ?>) jpaRepository).setUserLocalCache(userLocalCache);
            }
            return jpaRepository;
        }
        return super.getTargetRepository(information, entityManager);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        Class<?> repositoryInterface = metadata.getRepositoryInterface();
        if (JpaRepository.class.isAssignableFrom(repositoryInterface)) {
            return DefaultJpaRepository.class;
        }
        return SimpleJpaRepository.class;
    }
}
