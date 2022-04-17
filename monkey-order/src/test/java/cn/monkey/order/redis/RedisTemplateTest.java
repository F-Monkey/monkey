package cn.monkey.order.redis;

import cn.monkey.order.OrderApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest(classes = OrderApplication.class)
public class RedisTemplateTest {
    @Autowired
    RedisTemplate<String, Long> redisTemplate;

    @Test
    public void test01() {
        Long aLong = this.redisTemplate.opsForValue().get("111");
        System.out.println(aLong);
    }
}
