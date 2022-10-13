package cn.monkey.commons.data.repository;

import cn.monkey.commons.data.pojo.UserSession;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Mono;

public class ReactiveRedisUserSessionRepository implements ReactiveUserSessionRepository {

    private final ReactiveRedisTemplate<String, UserSession> redisTemplate;

    public ReactiveRedisUserSessionRepository(ReactiveRedisTemplate<String, UserSession> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<UserSession> findById(String id) {
        return this.redisTemplate.opsForValue().get(id);
    }
}
