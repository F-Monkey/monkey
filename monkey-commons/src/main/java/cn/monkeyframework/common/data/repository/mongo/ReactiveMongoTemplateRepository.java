package cn.monkeyframework.common.data.repository.mongo;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

public interface ReactiveMongoTemplateRepository {
    ReactiveMongoTemplate getTemplate();
}
