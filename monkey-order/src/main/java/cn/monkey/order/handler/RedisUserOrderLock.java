package cn.monkey.order.handler;

import cn.monkey.order.config.UserOrderLockProperties;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class RedisUserOrderLock implements UserOrderLock {

    private final RedisTemplate<String,String> redisTemplate;

    private final long lockTime;

    public RedisUserOrderLock(RedisTemplate<String,String> redisTemplate,
                              UserOrderLockProperties userOrderLockProperties){
        this.redisTemplate = redisTemplate;
        this.lockTime = userOrderLockProperties.getExpireMs();
    }

    @Override
    public boolean tryLock(String orderId, String uid) {
        Boolean absent = this.redisTemplate.opsForValue().setIfAbsent(orderId, uid, Duration.of(this.lockTime, ChronoUnit.SECONDS));
        return Boolean.TRUE.equals(absent);
    }

    @Override
    public int unlock(String orderId, String uid) {
        String s = this.redisTemplate.opsForValue().get(orderId);
        return s == null? UserOrderLock.ORDER_NOT_EXISTS : uid.equals(s)? UserOrderLock.SUCCESS : UserOrderLock.BAD_UID;
    }

}
