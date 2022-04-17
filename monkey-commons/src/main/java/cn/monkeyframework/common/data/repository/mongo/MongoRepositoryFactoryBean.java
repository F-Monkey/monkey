package cn.monkeyframework.common.data.repository.mongo;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

public class MongoRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean<T, S, ID> {
    /**
     * Creates a new {@link MongoRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public MongoRepositoryFactoryBean(Class<T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
        return new MongoRepositoryFactory(operations);
    }
}
