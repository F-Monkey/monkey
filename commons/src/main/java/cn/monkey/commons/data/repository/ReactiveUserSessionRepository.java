package cn.monkey.commons.data.repository;

import cn.monkey.commons.data.pojo.UserSession;
import reactor.core.publisher.Mono;

public interface ReactiveUserSessionRepository {
    Mono<UserSession> findById(String id);
}
