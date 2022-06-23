package cn.monkey.order.handler;

import com.google.common.base.Strings;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisOrderIdGenerator extends OrderIdGeneratorSupport {

    protected final RedisTemplate<String,Long> redisTemplate;
    protected final String orderIdKey;
    protected final String prefix;
    protected final int length;

    public RedisOrderIdGenerator(String orderIdKey,
                                 RedisTemplate<String,Long> redisTemplate,
                                 String prefix,
                                 int length){
        this.orderIdKey = orderIdKey;
        this.redisTemplate = redisTemplate;
        this.prefix = prefix;
        this.length = length;
    }

    @Override
    protected String genSuffix() {
        Long increment = this.redisTemplate.opsForValue().increment(this.orderIdKey);
        // 00000001  // 0_1
        //
        return Strings.padStart(String.valueOf(increment == null? 0L: increment),this.length,'0');
    }

    @Override
    public String newId() {
        return super.concat(this.prefix, this.genSuffix());
    }
}
