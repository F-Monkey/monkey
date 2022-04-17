package cn.monkeyframework.common.data.jpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class JpaAutoConfig {

    @Bean
    RedisTemplate<String, Long> stringLongRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Long> stringLongRedisTemplate = new RedisTemplate<>();
        stringLongRedisTemplate.setConnectionFactory(redisConnectionFactory);
        stringLongRedisTemplate.setDefaultSerializer(StringRedisSerializer.UTF_8);
        stringLongRedisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
        stringLongRedisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        stringLongRedisTemplate.setHashValueSerializer(StringRedisSerializer.UTF_8);
        stringLongRedisTemplate.afterPropertiesSet();
        return stringLongRedisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    RedisIdentifierGenerator redisIdentifierGenerator(@Qualifier("stringLongRedisTemplate") RedisTemplate<String, Long> stringLongRedisTemplate) {
        return new RedisIdentifierGenerator(stringLongRedisTemplate);
    }
}
