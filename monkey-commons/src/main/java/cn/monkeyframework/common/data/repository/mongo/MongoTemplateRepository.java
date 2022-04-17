package cn.monkeyframework.common.data.repository.mongo;

import org.springframework.data.mongodb.core.MongoOperations;

public interface MongoTemplateRepository {
    MongoOperations getMongoOperations();
}
