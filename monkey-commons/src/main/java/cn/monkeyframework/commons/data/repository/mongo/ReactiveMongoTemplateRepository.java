package cn.monkeyframework.commons.data.repository.mongo;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

public interface ReactiveMongoTemplateRepository {
    ReactiveMongoTemplate getTemplate();
}
