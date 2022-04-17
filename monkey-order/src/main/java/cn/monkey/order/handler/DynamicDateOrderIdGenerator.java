package cn.monkey.order.handler;

import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DynamicDateOrderIdGenerator extends RedisOrderIdGenerator {

    protected final DateTimeFormatter dateTimeFormatter;

    public DynamicDateOrderIdGenerator(String orderIdKey,
                                       RedisTemplate<String,Long> redisTemplate,
                                       String prefix,
                                       int length){
        super(orderIdKey, redisTemplate, prefix, length);
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    }

    @Override
    protected String genSuffix() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String s = super.genSuffix();
        return localDateTime.format(this.dateTimeFormatter)+s;
    }
}
