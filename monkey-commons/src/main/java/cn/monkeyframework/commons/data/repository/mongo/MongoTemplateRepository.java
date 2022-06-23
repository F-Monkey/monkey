package cn.monkeyframework.commons.data.repository.mongo;

import org.springframework.data.mongodb.core.MongoOperations;

public interface MongoTemplateRepository {
    MongoOperations getMongoOperations();
}
