package cn.monkeyframework.account.handler.redis;

import cn.monkeyframework.account.config.ExpandRedisProperties;
import cn.monkeyframework.account.handler.AppKeyHandler;
import com.google.common.base.Strings;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Mono;

public class SimpleRedisAppKeyHandler implements AppKeyHandler {

    private final ReactiveRedisTemplate<String, Long> redisTemplate;

    private final ExpandRedisProperties.AppKeyProperties appKeyProperties;

    public SimpleRedisAppKeyHandler(ReactiveRedisTemplate<String, Long> redisTemplate,
                                    ExpandRedisProperties.AppKeyProperties appKeyProperties) {
        this.redisTemplate = redisTemplate;
        this.appKeyProperties = appKeyProperties;
    }

    @Override
    public Mono<String> newAppKey() {
        Mono<Long> increment = this.redisTemplate.opsForValue().increment(this.appKeyProperties.getKey());
        return increment.map(l -> this.appKeyProperties.getSuffix() + Strings.padStart(String.valueOf(l == null ? 0L : l), this.appKeyProperties.getLength(), this.appKeyProperties.getPadding()));
    }
}
