package cn.monkeyframework.commons.data.repository.mongo;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.core.RepositoryMetadata;

public class MongoRepositoryFactory extends org.springframework.data.mongodb.repository.support.MongoRepositoryFactory {
    /**
     * Creates a new {@link MongoRepositoryFactory} with the given {@link MongoOperations}.
     *
     * @param mongoOperations must not be {@literal null}.
     */
    public MongoRepositoryFactory(MongoOperations mongoOperations) {
        super(mongoOperations);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        Class<?> repositoryInterface = metadata.getRepositoryInterface();
        if (repositoryInterface.isAssignableFrom(MongoTemplateRepository.class)) {
            return DefaultMongoRepository.class;
        }
        return SimpleMongoRepository.class;
    }
}
