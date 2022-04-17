package cn.monkey.order.config;

import cn.monkey.order.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    OrderIdGeneratorProperties orderIdGeneratorProperties(){
        return new OrderIdGeneratorProperties();
    }

    @Bean
    OrderIdGenerator orderIdGenerator(RedisConnectionFactory redisConnectionFactory,
                                      OrderIdGeneratorProperties orderIdGeneratorProperties){
        RedisTemplate<String, Long> stringLongRedisTemplate = new RedisTemplate<>();
        stringLongRedisTemplate.setConnectionFactory(redisConnectionFactory);
        stringLongRedisTemplate.setConnectionFactory(redisConnectionFactory);
        stringLongRedisTemplate.setDefaultSerializer(StringRedisSerializer.UTF_8);
        stringLongRedisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
        stringLongRedisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        stringLongRedisTemplate.setHashValueSerializer(StringRedisSerializer.UTF_8);
        stringLongRedisTemplate.afterPropertiesSet();
        return new DynamicDateOrderIdGenerator(orderIdGeneratorProperties.getKey(),stringLongRedisTemplate,orderIdGeneratorProperties.getPrefix(),orderIdGeneratorProperties.getLength());
    }

    @Bean
    UserOrderLockProperties userOrderLockProperties(){
        return new UserOrderLockProperties();
    }

    @Bean
    UserOrderLock userOrderLock(RedisConnectionFactory redisConnectionFactory,
                                UserOrderLockProperties userOrderLockProperties){
        RedisTemplate<String, String> stringLongRedisTemplate = new RedisTemplate<>();
        stringLongRedisTemplate.setConnectionFactory(redisConnectionFactory);
        stringLongRedisTemplate.setConnectionFactory(redisConnectionFactory);
        stringLongRedisTemplate.setDefaultSerializer(StringRedisSerializer.UTF_8);
        stringLongRedisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
        stringLongRedisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        stringLongRedisTemplate.setHashValueSerializer(StringRedisSerializer.UTF_8);
        stringLongRedisTemplate.afterPropertiesSet();
        return new RedisUserOrderLock(stringLongRedisTemplate,userOrderLockProperties);
    }
}
