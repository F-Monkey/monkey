package cn.monkeyframework.account.config;

import cn.monkeyframework.account.handler.AppKeyHandler;
import cn.monkeyframework.account.handler.redis.SimpleRedisAppKeyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    ReactiveRedisTemplate<String, Long> longReactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        RedisSerializationContext.RedisSerializationContextBuilder<String, Long> serializationContext = RedisSerializationContext.<String, Long>newSerializationContext();
        serializationContext.key(StringRedisSerializer.UTF_8);
        serializationContext.value(new GenericToStringSerializer<>(Long.class));
        serializationContext.hashKey(StringRedisSerializer.UTF_8);
        serializationContext.hashValue(new GenericToStringSerializer<>(Long.class));
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext.build());
    }

    @Bean
    ExpandRedisProperties expandRedisProperties() {
        return new ExpandRedisProperties();
    }

    @Bean
    AppKeyHandler appKeyHandler(ReactiveRedisTemplate<String, Long> longReactiveRedisTemplate,
                                ExpandRedisProperties expandRedisProperties) {
        ExpandRedisProperties.AppKeyProperties appKey = expandRedisProperties.getAppKey();
        return new SimpleRedisAppKeyHandler(longReactiveRedisTemplate, appKey);
    }
}
